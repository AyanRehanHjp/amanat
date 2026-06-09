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
function showMessage(text){
    const msg=document.getElementById("msg");
    msg.innerText=text;
    msg.style.display="block";
}

function login(){

    const userName=document.getElementById("userName").value.trim();
    const password=document.getElementById("password").value.trim();

     let captchaId = document.getElementById("captchaId").value;
     let captchaValue = document.getElementById("captchaValue").value;
    if(userName==="" || password===""){
        showMessage("Username and Password required");
        return;
    }

    showLoader();

    fetch(BASE_URL+"/signIn/verifyLogin",{
        method:"POST",
        headers:{
            "Content-Type":"application/json"
        },
        body:JSON.stringify({
            userName:userName,
            password:password,
            captchaId:captchaId,
            captchaValue:captchaValue
        })
    })
    .then(response => {

        if(!response.ok){
            return response.text().then(msg => { throw new Error(msg); });
        }

        return response.json();
    })
    .then(data => {

        console.log("Token response:",data);

        // save JWT token
        localStorage.setItem("token", data.token);
        localStorage.setItem("firstName", data.firstName);
        localStorage.setItem("lastName", data.lastName);
        localStorage.setItem("userId", data.userId);
        localStorage.setItem("role", data.role);
        localStorage.setItem("userName", userName);

        hideLoader();

        // popup
        showPopup("Login Successful");

        // ✔ popup ke OK click ke baad redirect
        window.redirectAfterPopup = "/welcome.html";

    })
    .catch(error=>{
        console.error(error);
        hideLoader();

        // 🔥 real backend message show hoga
        showPopup(error.message);
    })
    .finally(()=>{
        try{ hideLoader(); } catch(e){}
    });

}

// forgot button
function forgotHelp(){
    showPopup("Please contact admin via WhatsApp or Email.");
}
window.onload = function () {
    loadCaptcha();
}