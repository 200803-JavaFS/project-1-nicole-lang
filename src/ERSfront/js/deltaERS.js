const url = "http://localhost:8080/project1/";
document.getElementById("submit").addEventListener("click", loginFunc);
document.getElementById("submitRequest").addEventListener("click", createReimbFunc);
var loginForm = document.getElementById("login");
var buttonDiv = document.getElementById("buttons");
var wrongPassword = document.getElementById("popup");
var resultText;
var createReimb = document.createElement("button");
var usern;
var currentReimb;
var newStatus;
async function loginFunc(){

    if(!document.getElementById("result") == null)
    {
        resultText = document.getElementById("result");
    }else
    {
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

    if(resp.status===200){
        //show login success
        resultText.innerText = "Login successful";

    let data = await resp.json;
    let userType = data.userType;
        //list existing reimbursements
        listReimbFunc(userType);   

        if(userType == 1)
            {
                //create button for sending new request
                createReimb.addEventListener("click", showForm);
                createReimb.setAttribute("value", "New Request");
                buttonDiv.appendChild(createReimb);
            }
            let logout = document.createElement("button");
            logout.setAttribute("value", "Log out");
            logout.addEventListener("click", logoutFunc);
            buttonDiv.appendChild(logout);

    }else if(resp.status === 403){
        wrongPassword.setAttribute("hidden", false);
        document.getElementById("closePopup").addEventListener("click", closePopup);
    }else
        resultText.innerText = "Login failed";
    
}
function closePopup()
{
    wrongPassword.setAttribute("hidden", true);
    resultText.replaceWith(loginForm);
}
async function logoutFunc(){
    let resp = await fetch(url + "login", {
        method: "POST",
        credentials: "include"
    })
    if(resp.status === 200){
        //refresh page to return to login
        location.reload;
    }
    else{
        resultText.innerText = "Logout failed";
    }
}
function showForm(){
    document.getElementById("requestTable").setAttribute("hidden", false);
    createReimb.setAttribute("hidden", true);
}

async function showUpdate(id){
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
    cell.innerHTML = data.amt;
    row.appendChild(cell);
    let cell2 = document.createElement("td");
    cell2.innerHTML = data.desc;
    row.appendChild(cell2);
    let cell3 = document.createElement("td");
    cell3.innerHTML = data.submittedDate;
    row.appendChild(cell3);
    let cell4 = document.createElement("td");
    cell4.innerHTML = data.author;
    row.appendChild(cell4);
    let cell6 = document.createElement("td");
    switch(data.typeId){
        case 1:
            cell6.innerHTML = "Lodging";
            break;
        case 2:
            cell6.innerHTML = "Travel";
            break;
        case 3:
            cell6.innerHTML = "Food";
            break;
        case 4:
            cell6.innerHTML = "Other";
            break;
    }
    row.appendChild(cell6);
    let cell7 = document.createElement("td");
    if(currentReimb.statusId == 1){
        //provide buttons for approve/deny
        let approve = document.createElement("button");
        approve.addEventListener("click", approveReimbFunc());
        let deny = document.createElement("button");
        deny.addEventListener("click", denyReimbFunc());
        cell7.appendChild(approve);
        cell7.appendChild(deny);
        row.appendChild(cell7);
    }else
    {
        if(data.statusId == 2){
            cell7.innerHTML = "Approved";
        }else{
            cell7.innerHTML = "Denied";
        }
    }
    //show record
    reimbBody.appendChild(row);
}

async function createReimbFunc(){
    let rAmt = document.getElementById("amt").value;
    let rDesc = document.getElementById("desc").value;
    let typeList = document.getElementById("type");
    let rType = typeList.options[typeList.selectedIndex].value;
    
    let reimb = {
        amt: rAmt,
        desc: rDesc,
        typeId: rType,
        author: usern,
        statusId: 1 //Pending
    }

    let resp = await fetch(url + "reimb", {
        method : 'POST',
        body : json.stringify(reimb),
        credentials: 'include'
    });

    if (resp.status === 201) {
        resultText.innerText = "Reimbursement submitted successfully"
        //retrieve updated reimbursement list
        listReimbFunc();
        document.getElementById("requestTable").setAttribute("hidden", true);
    }
}
async function listReimbFunc(userType){
    resp = await fetch(url + "reimb", {
        method: "GET",
        credentials: 'include'
    })
    if (resp.status === 200) {
        
        let data = await resp.json();
        reimbBody = document.getElementById("reimb");
        reimbBody.innerHTML = "";
        //display the reimbursement table
            document.getElementById("reimbTable").setAttribute("hidden", false);
            for(let reimb of data){
                //insert list of reimbursements into the tbody element with ID "reimb"
                console.log(reimb);
                let row = document.createElement("tr");
                let cell = document.createElement("td");
                
                if(userType == 2)
                {//if user is a financial manager, add anchor to each reimbId that will enable approve/deny
                    let idLink = document.createElement("a");
                    idLink.addEventListener("click", showUpdate(reimb.reimbId));
                }else
                {
                    cell.innerHTML = reimb.reimbId;
                }
                row.appendChild(cell);
                let cell2 = document.createElement("td");
                cell2.innerHTML = reimb.amt;
                row.appendChild(cell2);
                let cell3 = document.createElement("td");
                cell3.innerHTML = reimb.desc;
                row.appendChild(cell3);
                let cell4 = document.createElement("td");
                cell4.innerHTML = reimb.submittedDate;
                row.appendChild(cell4);
                let cell5 = document.createElement("td");
                cell5.innerHTML = reimb.author;
                row.appendChild(cell5);
                let cell6 = document.createElement("td");
                switch(reimb.statusId){
                    case 1:
                        cell6.innerHTML = "Pending";
                        break;
                    case 2:
                        cell6.innerHTML = "Approved";
                        break;
                    case 3:
                        cell6.innerHTML = "Denied";
                        break;    
                }
                row.appendChild(cell6);
                let cell7 = document.createElement("td");
                switch(reimb.typeId){
                    case 1:
                        cell7.innerHTML = "Lodging";
                        break;
                    case 2:
                        cell7.innerHTML = "Travel";
                        break;
                    case 3:
                        cell7.innerHTML = "Food";
                        break;
                    case 4:
                        cell7.innerHTML = "Other";
                        break;
                }
                row.appendChild(cell7);
                reimbBody.appendChild(row);
            }
    }else if(resp.status === 204)
    {
        resultText.innerText = "No reimbursements found!"
    }
}
function approveReimbFunc(){
    updateReimbFunc(2);
}
function denyReimbFunc(){
    updateReimbFunc(3);
}
async function updateReimbFunc(newStatus){  
    let reimb = {
        reimbId: currentReimb.reimbId,
        statusId: newStatus,
        resolver: usern
    }
    let resp = await fetch(url + "reimb", {
        method : 'PUT',
        body : json.stringify(reimb),
        credentials: 'include'
    });

    if (resp.status === 202) {
        resultText.innerText = "Reimbursement updated successfully"
    }
    else{
        resultText.innerText = "Failed to update reimbursement"
    }
    //reload list
    listReimbFunc();
}