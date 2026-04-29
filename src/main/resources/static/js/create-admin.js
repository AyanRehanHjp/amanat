function createAdmin(){

    let fullName = document.getElementById("fullName").value.trim();
    // remove extra spaces from start and end

    let designation = document.getElementById("designation").value.trim();
    // clean input

    let userId = document.getElementById("userId").value.trim().toUpperCase();
    // convert to uppercase to avoid duplicate like adm001 vs ADM001

    let password = document.getElementById("password").value.trim();
    // clean password input

    if(!fullName || !designation || !userId || !password){
        showMsg("All fields are required");
        // check empty fields
        return;
    }

    if(!/^[A-Za-z ]+$/.test(fullName)){
        showMsg("Full Name should contain only alphabets");
        // regex validation for name
        return;
    }

    if(!/^[A-Za-z ]+$/.test(designation)){
        showMsg("Designation should contain only alphabets");
        // prevent invalid designation
        return;
    }

    if(!/^[A-Za-z0-9]+$/.test(userId)){
        showMsg("Admin ID should not contain spaces or special characters");
        // restrict format
        return;
    }

    if(!/(?=.*[A-Z])(?=.*[0-9]).{6,12}/.test(password)){
        showMsg("Password must contain uppercase and number");
        // strong password check
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
    .then(res => {
        if(!res.ok){
            return res.text().then(msg => { throw new Error(msg); });
            // catch backend validation errors like duplicate
        }
        return res.text();
    })
    .then(msg => {
        showMsg(msg);
    })
    .catch(err => {
        showMsg(err.message);
        // show backend error message
    });
}

function showMsg(msg){
    let popup = document.getElementById("popup");
    let text = document.getElementById("popupMsg");

    text.innerText = msg;
    popup.style.display = "flex";

    setTimeout(() => {
        popup.style.display = "none";
    }, 3000); // 3 sec baad close
}

document.getElementById("adminForm").addEventListener("submit", function(e){
    if(!this.checkValidity()){
    e.preventDefault();   //  invalid hai to yahi ruk jayega
             return;
    }
    e.preventDefault();   //
     createAdmin();
});