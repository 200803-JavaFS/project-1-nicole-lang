const url = "http://localhost:8080/project1/";
document.getElementById("submit").addEventListener("click", loginFunc);
var loginForm = document.getElementById("login");
var buttonDiv = document.getElementById("buttons");
var popup = document.getElementById("popup");
var resultText;
var createReimb = document.createElement("button");
var usern;
var currentReimb;
var newStatus;
var uType;
async function loginFunc() {

    if (!document.getElementById("result") == null) {
        resultText = document.getElementById("result");
    } else {
        resultText = document.createElement("p");
        resultText.setAttribute("id", "result");
    }

    resultText.innerText = "Checking database...";

    usern = document.getElementById("username").value;
    let userp = document.getElementById("password").value;

    let user = {
        username: usern,
        password: userp
    }
    loginForm.replaceWith(resultText);

    let resp = await fetch(url + "login", {
        method: "POST",
        body: JSON.stringify(user),
        credentials: "include"
    })

    if (resp.status === 200) {
        //show login success
        resultText.innerText = "Login successful";

        let data = await resp.json();
        console.log(data);
        uType = data.typeID;
        //list existing reimbursements
        listReimbFunc();
        if (uType == 1) {
            //create button for sending new request
            createReimb.addEventListener("click", showForm);
            createReimb.innerText = "New Request";
            buttonDiv.appendChild(createReimb);
        }
        let logout = document.createElement("button");
        logout.setAttribute("id", "logout");
        logout.innerText = "Log out";
        logout.addEventListener("click", logoutFunc);
        buttonDiv.appendChild(logout);

    } else if (resp.status === 403) {
        popup.removeAttribute("hidden");
        document.getElementById("popupText").innerHTML = "Incorrect Password";
        document.getElementById("closePopup").addEventListener("click", closePopup);
    } else {
        popup.removeAttribute("hidden");
        document.getElementById("popupText").innerHTML = "User " + usern + " does not exist";
        document.getElementById("closePopup").addEventListener("click", closePopup);
        resultText.replaceWith(loginForm);
    }
}

function closePopup() {
    popup.setAttribute("hidden", true);
    resultText.replaceWith(loginForm);
}
async function logoutFunc() {
    let resp = await fetch(url + "logout", {
        method: "POST",
        credentials: "include"
    })
    if (resp.status === 200) {
        resultText.replaceWith(loginForm);
        document.getElementById("username").setAttribute("value", "");
        document.getElementById("password").setAttribute("value", "");
        document.getElementById("reimbTable").setAttribute("hidden", true);
        document.getElementById("requestTable").setAttribute("hidden", true);
        buttonDiv.innerHTML = "";
    } else {
        resultText.innerText = "Logout failed";
    }
}

function showForm() {
    resultText.innerText = "";
    document.getElementById("requestTable").removeAttribute("hidden");
    createReimb.setAttribute("hidden", true);
    let submitRequest = document.getElementById("submitRequest");
    submitRequest.addEventListener("click", createReimbFunc);
}

async function showUpdate(id) {
    //focuses on a single reimbursement in the list and allows update if it is a pending request
    resp = await fetch(url + "reimb/" + id, {
        method: "GET",
        credentials: 'include'
    })
    currentReimb = await resp.json();

    let reimbBody = document.getElementById("reimb");
    reimbBody.innerHTML = "";
    let cell = document.createElement("td");
    let row = document.createElement("tr");
    cell.innerText = currentReimb.amt;
    row.appendChild(cell);
    let cell2 = document.createElement("td");
    cell2.innerText = currentReimb.desc;
    row.appendChild(cell2);
    let cell3 = document.createElement("td");
    cell3.innerText = currentReimb.submittedDate;
    row.appendChild(cell3);
    let cell4 = document.createElement("td");
    cell4.innerText = currentReimb.author;
    row.appendChild(cell4);
    let cell6 = document.createElement("td");
    switch (currentReimb.typeId) {
        case 1:
            cell6.innerText = "Lodging";
            break;
        case 2:
            cell6.innerText = "Travel";
            break;
        case 3:
            cell6.innerText = "Food";
            break;
        case 4:
            cell6.innerText = "Other";
            break;
    }
    row.appendChild(cell6);
    let cell7 = document.createElement("td");
    if (currentReimb.statusId == 1) {
        //provide buttons for approve/deny
        let approve = document.createElement("button");
        approve.addEventListener("click", approveReimbFunc());
        let deny = document.createElement("button");
        deny.addEventListener("click", denyReimbFunc());
        cell7.appendChild(approve);
        cell7.appendChild(deny);
        row.appendChild(cell7);
    } else {
        if (currentReimb.statusId == 2) {
            cell7.innerText = "Approved";
        } else {
            cell7.innerText = "Denied";
        }
    }
    //show record
    reimbBody.appendChild(row);
}

async function createReimbFunc() {
    let amtInput = document.getElementById("amt");
    let rAmt = amtInput.value;
    let descInput = document.getElementById("desc");
    let rDesc = descInput.value;
    let typeList = document.getElementById("type");
    let rType = typeList.options[typeList.selectedIndex].value;

    amtInput.setAttribute("value", "");
    descInput.setAttribute("value", "");

    let reimb = {
        amt: rAmt,
        desc: rDesc,
        typeId: rType,
        author: usern,
        statusId: 1 //Pending
    }

    let resp = await fetch(url + "reimb", {
        method: 'POST',
        body: JSON.stringify(reimb),
        credentials: 'include'
    });

    if (resp.status === 201) {
        resultText.innerText = "Reimbursement submitted successfully"
        //retrieve updated reimbursement list
        listReimbFunc();
        document.getElementById("requestTable").setAttribute("hidden", true);
    }
}
async function listReimbFunc() {
    resp = await fetch(url + "reimb", {
        method: "GET",
        credentials: 'include'
    })
    if (resp.status === 200) {

        let data = await resp.json();
        reimbBody = document.getElementById("reimb");
        reimbBody.innerHTML = "";
        //display the reimbursement table
        document.getElementById("reimbTable").removeAttribute("hidden");
        for (let reimb of data) {
            //insert list of reimbursements into the tbody element with ID "reimb"
            console.log(reimb);
            let row = document.createElement("tr");
            let cell = document.createElement("td");
            if (uType == 2) { //if user is a financial manager, add anchor to each reimbId that will enable approve/deny
                let idLink = document.createElement("button");
                idLink.addEventListener("click", showUpdate(reimb.reimbId));
                idLink.innerText = reimb.reimbId;
                cell.appendChild(idLink);
            } else {
                cell.innerText = reimb.reimbId;
            }
            row.appendChild(cell);

            let cell2 = document.createElement("td");
            cell2.setAttribute("class", "amt");
            cell2.innerText = reimb.amt;
            row.appendChild(cell2);

            let cell3 = document.createElement("td");
            cell3.setAttribute("class", "desc");
            cell3.innerText = reimb.desc;
            row.appendChild(cell3);

            let cell4 = document.createElement("td");
            switch (reimb.typeId) {
                case 1:
                    cell4.innerText = "Lodging";
                    break;
                case 2:
                    cell4.innerText = "Travel";
                    break;
                case 3:
                    cell4.innerText = "Food";
                    break;
                case 4:
                    cell4.innerText = "Other";
                    break;
            }
            row.appendChild(cell4);

            let cell5 = document.createElement("td");
            cell5.innerText = reimb.submittedDate;
            row.appendChild(cell5);

            let cell6 = document.createElement("td");
            cell6.innerText = reimb.author;
            row.appendChild(cell6);

            let cell7 = document.createElement("td");
            switch (reimb.statusId) {
                case 1:
                    cell7.innerText = "Pending";
                    break;
                case 2:
                    cell7.innerText = "Approved";
                    break;
                case 3:
                    cell7.innerText = "Denied";
                    break;
            }
            row.appendChild(cell7);

            reimbBody.appendChild(row);
        }
    } else if (resp.status === 204) {
        resultText.innerText = "No reimbursements found!"
    }
}

function approveReimbFunc() {
    updateReimbFunc(2);
}

function denyReimbFunc() {
    updateReimbFunc(3);
}
async function updateReimbFunc(newStatus) {
    let reimb = {
        reimbId: currentReimb.reimbId,
        statusId: newStatus,
        resolver: usern
    }
    let resp = await fetch(url + "reimb", {
        method: 'PUT',
        body: JSON.stringify(reimb),
        credentials: 'include'
    });

    if (resp.status === 202) {
        resultText.innerText = "Reimbursement updated successfully"
    } else {
        resultText.innerText = "Failed to update reimbursement"
    }
    //reload list
    listReimbFunc();
}