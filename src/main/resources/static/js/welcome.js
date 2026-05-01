/* check JWT token */
const token = localStorage.getItem("token");
const role  = localStorage.getItem("role");

if(!token || role.toUpperCase() !== "USER"){
    window.location.href="/login.html";
}

/* read name */
const firstName = localStorage.getItem("firstName");
const lastName  = localStorage.getItem("lastName");

if(firstName && lastName){
    document.getElementById("welcomeText").innerText =
    "Welcome, " + firstName + " " + lastName + " 🎉";
}

/* logout */
function logout(){
    localStorage.clear();
    window.location.href="/login.html";
}

