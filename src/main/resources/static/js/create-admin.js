function createAdmin(){

    let fullName = document.getElementById("fullName").value;
    let designation = document.getElementById("designation").value;
    let userId = document.getElementById("userId").value;
    let password = document.getElementById("password").value;

    if(!fullName || !designation || !userId || !password){
        showMsg("All fields are required");
        return;
    }

    let data = {
        fullName: fullName,
        designation: designation,
        userId: userId,
        password: password
    };

    fetch("/admins/create", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(res => res.text())
    .then(msg => {
        showMsg(msg);
    })
    .catch(err => {
        showMsg("Error creating admin");
    });
}

function showMsg(msg){
    let box = document.getElementById("msgBox");
    let text = document.getElementById("msgText");

    text.innerText = msg;
    box.style.display = "block";
}