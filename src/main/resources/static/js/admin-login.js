function loadCaptcha(){

    fetch("/captcha/generate")
    .then(res => res.json())
    .then(data => {

        document.getElementById("captchaText")
                .innerText = data.captcha;

        document.getElementById("captchaId")
                .value = data.captchaId;
    });
}

function adminLogin(){

    let adminId = document.getElementById("adminId").value;
    let password = document.getElementById("password").value;

    let captchaId = document.getElementById("captchaId").value;
    let captchaValue = document.getElementById("captchaValue").value;
    if(!adminId || !password || !captchaValue){
        showPopup("Please enter Admin ID and Password and Captcha");
        return;
    }

    // loader show
    document.getElementById("loader").style.display = "flex";

    fetch("/admins/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            userId: adminId,
            password: password,
            captchaId: captchaId,
            captchaValue: captchaValue

        })
    })
    .then(res => {
        if(!res.ok){
            throw new Error("Invalid credentials");
        }
        return res.json(); //getting token and role
    })
    .then(data => {

        // 🔥 token and role store
        localStorage.setItem("token", data.token);
        localStorage.setItem("role", data.role);
        // 🔥 logged in admin id store
        localStorage.setItem("userId", adminId);
        showPopup("Login Successful");

        window.redirectAfterPopup = "/admin.html";
    })
    .catch(err => {
        showPopup(err.message || "Login Failed");
    })
    .finally(() => {
        document.getElementById("loader").style.display = "none";
    });
}

function showMsg(msg){
    let box = document.getElementById("msg");
    box.innerText = msg;
    box.style.display = "block";
}

window.onload = function () {
    loadCaptcha();
}