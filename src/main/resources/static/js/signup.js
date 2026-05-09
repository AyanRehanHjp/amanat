const BASE_URL="http://localhost:9000";

function clearErrors(){

document.querySelectorAll(".error").forEach(e=>{
e.innerText="";
e.style.display="none";
});

document.querySelectorAll("input").forEach(e=>{
e.classList.remove("input-error");
});

document.getElementById("msgBox").style.display="none";
}

function setFieldError(id,message){

const error=document.getElementById(id+"Error");

error.innerText=message;
error.style.display="block";

document.getElementById(id).classList.add("input-error");
}

function showTopMessage(message){
document.getElementById("msgText").innerText=message;
document.getElementById("msgBox").style.display="block";
}

function signup(){

clearErrors();

let valid=true;

const firstName=document.getElementById("firstName").value.trim();
const lastName=document.getElementById("lastName").value.trim();
const mobile=document.getElementById("mobile").value.trim();
const countryCode =
document.getElementById("countryCode").value;

const fullMobile =
countryCode + mobile;

const email=document.getElementById("email").value.trim();
const userName=document.getElementById("userName").value.trim();
const password=document.getElementById("password").value.trim();
const confirmPassword=document.getElementById("confirmPassword").value.trim();
const role="user";

// VALIDATIONS
if(firstName===""){
setFieldError("firstName","First Name is mandatory");
valid=false;
}

if(lastName===""){
setFieldError("lastName","Last Name is mandatory");
valid=false;
}

if(mobile===""){
setFieldError("mobile","Mobile is mandatory");
valid=false;
}
else if(!/^[0-9]{6,15}$/.test(mobile)){
setFieldError(
"mobile",
"Enter valid mobile number"
);
valid=false;
}

if(email===""){
setFieldError("email","Email is mandatory");
valid=false;
}

if(userName===""){
setFieldError("userName","Username is mandatory");
valid=false;
}

if(password===""){
setFieldError("password","Password is mandatory");
valid=false;
}

if(confirmPassword===""){
setFieldError("confirmPassword","Confirm Password is mandatory");
valid=false;
}
else if(password!==confirmPassword){
setFieldError("confirmPassword","Passwords do not match");
valid=false;
}

if(!valid) return;

// API CALL
fetch(BASE_URL+"/signUp/addUser",{
method:"POST",
headers:{
"Content-Type":"application/json"
},
body:JSON.stringify({
firstName,
lastName,
mobile:fullMobile,
email,
userName,
password,
role
})
})
.then(res => {
    if(!res.ok){
        return res.text().then(msg => { throw new Error(msg); });
    }
    return res.json();
})
.then(data=>{

    // SUCCESS CASE
    showPopup("Thank You for Signup, \n Your Request sent to Admin, After Approval You can login with same username and Password\n Your User Name is: "+userName);

    window.redirectAfterPopup = "/login.html";

})
.catch(err=>{
    // real error show (duplicate email, username etc.)
    showTopMessage(err.message);
    showPopup(err.message);
});

}

// PASSWORD TOGGLE
function togglePassword(){

const pass=document.getElementById("password");

if(pass.type==="password"){
pass.type="text";
}else{
pass.type="password";
}

}