const url = "http://localhost:8080/project1/";
document.getElementById('submit').addEventListener("click", loginFunc);
var loginForm = document.getElementById("login")
var resultText;
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

    let usern = document.getElementById("username").value;
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
        
        //list existing reimbursements
        listReimbFunc();   

        //create button for sending new request
        let createReimb = document.createElement("button");
        createReimb.addEventListener("click", createReimbFunc);
        createReimb.innerText = ""
        
    }else
        resultText.innerText = "Login failed";
    
}

async function createReimbFunc(){
    document.getElementById("avbody").innerText ="";

    let resp = await fetch(url + "reimb", {
        method : 'POST',
        credentials: 'include'
    });

    if (resp.status === 200) {
        resultText.innerText = "Reimbursement submitted successfully"
        //retrieve updated reimbursement list
        listReimbFunc();
    }
}
async function listReimbFunc(){
    resp = await fetch(url + "reimb", {
        method: "GET",
        credentials: 'include'
    })
    if (resp.status === 200) {
        
        let data = await resp.json();
        reimbBody = document.getElementById("reimb");
        reimbBody.innerHTML = "";
        if(data!= '{}')
        {   //display the reimbursement table
            document.getElementById("reimbTable").removeAttribute("hidden");
            for(let reimb of data){
                //insert list of reimbursements into the tbody element with ID "reimb"
                console.log(reimb);
                let row = document.createElement("tr");
                let cell = document.createElement("td");
                cell.innerHTML = reimb.reimbId;
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
                cell6.innerHTML = reimb.status;
                row.appendChild(cell6);
                let cell7 = document.createElement("td");
                cell7.innerHTML = reimb.type;
                row.appendChild(cell7);
                reimbBody.appendChild(row);
            }
        }else{
            resultText.innerText = "No reimbursements found!"
        }
    }
   
}