const BASE_URL="http://localhost:9000";

function showMessage(text){
    const msg=document.getElementById("msg");
    msg.innerText=text;
    msg.style.display="block";
}

function login(){

    const userName=document.getElementById("userName").value.trim();
    const password=document.getElementById("password").value.trim();

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
            password:password
        })

    })
    .then(response => {

        if(!response.ok){
            throw new Error("Invalid credentials");
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

        // save username
        localStorage.setItem("userName", userName);

        // redirect to welcome page
hideLoader();

// 👇 popup show karo
showPopup("Login Successful");

// 👇 2 sec baad redirect
setTimeout(()=>{
    window.location.href="/welcome.html";
},2000);
    })
    .catch(error=>{
        console.error(error);
        hideLoader();
        showPopup("Invalid Username or Password");
    })
    .finally(()=>{
        // ensure loader hidden in all cases (if redirect happens this may not run, but safe)
        try{ hideLoader(); } catch(e){}
    });

}

function forgotHelp(){
    showPopup("Please contact admin via WhatsApp or Email.");
}