function createAdmin(){

    let fullName = document.getElementById("fullName").value.trim();
    let designation = document.getElementById("designation").value.trim();
    let userId = document.getElementById("userId").value.trim().toUpperCase();
    let password = document.getElementById("password").value.trim();

    if(!fullName || !designation || !userId || !password){
        showMsg("All fields are required", false);
        return;
    }

    if(!/^[A-Za-z ]+$/.test(fullName)){
        showMsg("Full Name should contain only alphabets", false);
        return;
    }

    if(!/^[A-Za-z ]+$/.test(designation)){
        showMsg("Designation should contain only alphabets", false);
        return;
    }

    if(!/^[A-Za-z0-9]+$/.test(userId)){
        showMsg("Admin ID should not contain spaces or special characters", false);
        return;
    }

    if(!/(?=.*[A-Z])(?=.*[0-9]).{6,12}/.test(password)){
        showMsg("Password must contain uppercase and number", false);
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
        }
        return res.text();
    })
    .then(msg => {
        showMsg(msg, true);
        document.getElementById("adminForm").reset();
    })
    .catch(err => {
        showMsg(err.message, false);
    });
}

function showMsg(msg, isSuccess){
    let popup = document.getElementById("popup");
    let content = popup.querySelector(".popup-content"); // 🔥 important
    let title = document.getElementById("popupTitle");
    let subTitle = document.getElementById("popupSubTitle");
    let text = document.getElementById("popupMsg");

    text.innerText = msg;

    if(isSuccess){
        title.innerText = "🎉 Congratulations!";
        subTitle.innerText = "New Admin Created Successfully";
        content.style.background = "#28a745"; // ✅ GREEN
    } else {
        title.innerText = "❌ Error";
        subTitle.innerText = "";
        content.style.background = "#dc3545"; // ✅ RED
    }

    popup.style.display = "flex";

    setTimeout(() => {
        popup.style.display = "none";
    }, 5000); // ✅ 5 sec
}

// ❌ close button function
function closePopup(){
    document.getElementById("popup").style.display = "none";
}

document.getElementById("adminForm").addEventListener("submit", function(e){
    e.preventDefault();

    if(!this.checkValidity()){
        return;
    }

    createAdmin();
});