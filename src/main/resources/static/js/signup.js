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

function showPopup(message,success){

const popup=document.getElementById("popup");

popup.innerHTML = success
? "✔ " + message
: "✖ " + message;

popup.className = success ? "success" : "error";

popup.style.display="block";

setTimeout(()=>{
popup.style.opacity="1";
popup.style.transform="translateY(0)";
},10);

setTimeout(()=>{
popup.style.opacity="0";
popup.style.transform="translateY(-20px)";

setTimeout(()=>{
popup.style.display="none";
},400);

},3000);

}

function signup(){

clearErrors();

let valid=true;

const firstName=document.getElementById("firstName").value.trim();
const lastName=document.getElementById("lastName").value.trim();
const mobile=document.getElementById("mobile").value.trim();
const email=document.getElementById("email").value.trim();
const userName=document.getElementById("userName").value.trim();
const password=document.getElementById("password").value.trim();
const confirmPassword=document.getElementById("confirmPassword").value.trim();
const role=document.getElementById("role").value;

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

fetch(BASE_URL+"/signUp/addUser",{

method:"POST",

headers:{
"Content-Type":"application/json"
},

body:JSON.stringify({
firstName,
lastName,
mobile,
email,
userName,
password,
role
})

})
.then(res=>res.json())
.then(data=>{

if(data.msg && data.msg.toLowerCase().includes("fail")){

showTopMessage(data.msg);
showPopup(data.msg,false);

}
else{

const userId = data.userId;

showPopup("Signup Successful 🎉 Your User Name is: "+userName,true);

setTimeout(()=>{
window.location.href="/login.html";
},2500);

}

})
.catch(()=>{
showPopup("Server Error",false);
});

}

function togglePassword(){

const pass=document.getElementById("password");

if(pass.type==="password"){
pass.type="text";
}else{
pass.type="password";
}

}