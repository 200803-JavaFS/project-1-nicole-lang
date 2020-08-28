document.getElementById('submit').onclick = getData();
async function getData(){
    let userName = document.getElementById('username').value;
    let password = document.getElementById('password').value;

    let response = await fetch('index.html'+userName+password+'/')
}