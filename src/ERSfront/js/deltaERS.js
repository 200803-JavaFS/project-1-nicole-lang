const url = "http://localhost:8080/project1/";
document.getElementById("submit").addEventListener("click", loginFunc);
//declare global variables
var loginForm = document.getElementById("login");
var buttonDiv = document.getElementById("buttons");
var popup = document.getElementById("popup");
var resultText;
var createReimb = document.createElement("button");
var usern;
var currentReimb;
var newStatus;
var uType;
var userExists = false;

async function loginFunc() {
//get user input and do login POST
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
        //show logout button
        let logout = document.createElement("button");
        logout.setAttribute("id", "logout");
        logout.innerText = "Log out";
        logout.addEventListener("click", logoutFunc);
        buttonDiv.appendChild(logout);

    } else if (resp.status === 403) {
        //wrong password
        popup.removeAttribute("hidden");
        document.getElementById("popupText").innerHTML = "Incorrect Password";
        document.getElementById("closePopup").addEventListener("click", closePopup);
        userExists = true;
    } else {
        //wrong username
        popup.removeAttribute("hidden");
        document.getElementById("popupText").innerHTML = "User " + usern + " does not exist";
        document.getElementById("closePopup").addEventListener("click", closePopup);
        resultText.replaceWith(loginForm);
    }
}

function closePopup() {
    //runs when popup OK button is clicked
    resultText.replaceWith(loginForm);
    if(userExists){
        //keep username input entered but clear the password field
        let passwordInput = document.getElementById("password");
        passwordInput.value = "";
        passwordInput.focus();
        popup.setAttribute("hidden", true);
    }else{
        location.reload();
    }
    
}
async function logoutFunc() {
    //do logout POST (clear session variables)
    let resp = await fetch(url + "logout", {
        method: "POST",
        credentials: "include"
    })
    if (resp.status === 200) {
        location.reload();
    } else {
        resultText.innerText = "Logout failed";
    }
}

function showForm() {
    //show form for adding new request
    resultText.innerText = "";
    document.getElementById("requestTable").removeAttribute("hidden");
    createReimb.setAttribute("hidden", true);
    let submitRequest = document.getElementById("submitRequest");
    submitRequest.addEventListener("click", createReimbFunc);
    let logoutBtn = document.getElementById("logout");

    showReturn();
}

async function showUpdate(e) {
    //focuses on a single reimbursement in the list and allows update if it is a pending request
    curId = e.getAttribute("id");
    //do 'reimb/#' GET
    resp = await fetch(url + "reimb/" + curId, {
        method: "GET",
        credentials: 'include'
    })
    currentReimb = await resp.json();

    //build update form table
    let reimbBody = document.getElementById("reimb");
    reimbBody.innerHTML = "";

    let urow = document.createElement("tr");
    
    let ucell = document.createElement("td");
    ucell.innerText = currentReimb.reimbId;
    urow.appendChild(ucell);

    let ucell1 = document.createElement("td");
    ucell1.innerText = currentReimb.amt;
    urow.appendChild(ucell1);

    let ucell2 = document.createElement("td");
    ucell2.innerText = currentReimb.desc;
    urow.appendChild(ucell2);

    let ucell3 = document.createElement("td");
    switch (currentReimb.typeId) {
        case 1:
            ucell3.innerText = "Lodging";
            break;
        case 2:
            ucell3.innerText = "Travel";
            break;
        case 3:
            ucell3.innerText = "Food";
            break;
        case 4:
            ucell3.innerText = "Other";
            break;
    }
    urow.appendChild(ucell3);

    let ucell4 = document.createElement("td");
    ucell4.innerText = currentReimb.submittedDate;
    urow.appendChild(ucell4);

    let ucell5 = document.createElement("td");
    ucell5.innerText = currentReimb.author;
    urow.appendChild(ucell5);

    //provide buttons for approve/deny
    let ucell6 = document.createElement("td");
    ucell6.innerHTML = '<a id = "approve" href = "javascript:void(0)" onClick = "approveReimbFunc()">&nbsp;&#10003&nbsp;</a>' +
                        '&nbsp;<a id = "deny" href = "javascript:void(0)" onClick = "denyReimbFunc()">&nbsp;X&nbsp;</a>';
    urow.appendChild(ucell6);

    let rd = document.getElementById("resolveDate");
    rd.setAttribute("hidden", true);
    //show record
    reimbBody.appendChild(urow);
    let logoutBtn = document.getElementById("logout");

    showReturn();
}

function showReturn(){
    //provide a button that returns to the list (cancelling update); insert before logout button
    let returnBtn = document.createElement("button");
    let logoutBtn = document.getElementById("logout");
    returnBtn.setAttribute("id", "returnBtn");
    returnBtn.innerText = "Cancel";
    returnBtn.addEventListener("click", listReimbFunc);
    buttonDiv.removeChild(logoutBtn);
    buttonDiv.appendChild(returnBtn);
    buttonDiv.appendChild(logoutBtn);
}

async function createReimbFunc() {
    //get user input and do reimb(create) POST
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
    //hide/show elements based on current context
    if(document.getElementById("returnBtn")!= null)
    {
        document.getElementById("requestTable").setAttribute("hidden", true);
        let returnBtn = document.getElementById("returnBtn");
        buttonDiv.removeChild(returnBtn);
        if(uType == 1){
            let logoutBtn = document.getElementById("logout");
            buttonDiv.removeChild(logoutBtn);
            createReimb.removeAttribute("hidden");
            buttonDiv.appendChild(createReimb);
            buttonDiv.appendChild(logoutBtn);
        }
    }
    let rd = document.getElementById("resolveDate");
    if(rd.getAttribute("hidden") != null)
        rd.removeAttribute("hidden");

    //do reimb(list) GET
    resp = await fetch(url + "reimb", {
        method: "GET",
        credentials: 'include'
    });
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
            let cell1 = document.createElement("td");
            cell1.innerText = reimb.reimbId;   
            row.appendChild(cell1);

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
                    if(uType == 2){//add update link to the status field if current user is a financial manager
                        cell7.innerHTML = '<a id = "' + reimb.reimbId + '" href = "javascript:void(0)" onClick = "showUpdate(this)">Pending</a>';
                    }
                    else{//employees should not be able to change their reimbursement status
                        cell7.innerText = "Pending";
                    }                  
                    break;
                case 2:
                    cell7.innerText = "Approved";
                    break;
                case 3:
                    cell7.innerText = "Denied";
                    break;
            }
            row.appendChild(cell7);

            let cell8 = document.createElement("td");
            //show resolved date if available
            if(reimb.resolvedDate != null){
                cell8.innerText = reimb.resolvedDate + " (by " + reimb.resolver + ")";
            }  
            row.appendChild(cell8);

            
            reimbBody.appendChild(row);
        }
    } else if (resp.status === 204) {
        //it's possible to have no viewable reimbursements
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
    //get needed data and do reimb PUT
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