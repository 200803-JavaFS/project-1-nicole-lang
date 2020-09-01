let url = "http://localhost:8080/project1/";
document.getElementById('submit').addEventListener("click", loginFunc);

async function loginFunc(){
    let usern = document.getElementById("username").value;
    let userp = document.getElementById("password").value;
    
    let user = {
        username: usern,
        password: userp
    }
    
    let resp = await fetch(url + "login", {
        method: "POST",
        body: JSON.stringify(user),
        credentials: "include"
    })

    let resultText = document.createElement("p");
    if(resp.status===200){
        //show login success
        resultText.innerText = "Login successful";
        let createReimb = document.createElement("button");
        createReimb.addEventListener("click", createReimbFunc);
    }else
        resultText.innerText = "Login failed";
    document.getElementById("login").appendChild(resultText);
}

async function createReimbFunc(){
    document.getElementById("avbody").innerText ="";

    let resp = await fetch(url + "avenger", {
        credentials: 'include',
    });

    if (resp.status === 200) {
        let data = await resp.json();
        /* for (let avenger of data) {
            console.log(avenger);
            let row = document.createElement("tr");
            let cell = document.createElement("td");
            cell.innerHTML = avenger.supId;
            row.appendChild(cell);
            let cell2 = document.createElement("td");
            cell2.innerHTML = avenger.supName;
            row.appendChild(cell2);
            let cell3 = document.createElement("td");
            cell3.innerHTML = avenger.supPower;
            row.appendChild(cell3);
            let cell4 = document.createElement("td");
            cell4.innerHTML = avenger.firstName;
            row.appendChild(cell4);
            let cell5 = document.createElement("td");
            cell5.innerHTML = avenger.lastName;
            row.appendChild(cell5);
            let cell6 = document.createElement("td");
            cell6.innerHTML = avenger.powerLevel;
            row.appendChild(cell6);
            if (avenger.homeBase != null) {
                let cell7 = document.createElement("td");
                cell7.innerHTML = avenger.homeBase.homeBase;
                row.appendChild(cell7);
            } else {
                let cell7 = document.createElement("td");
                row.appendChild(cell7);
            }
            document.getElementById("avbody").appendChild(row);
        } */
    }
}