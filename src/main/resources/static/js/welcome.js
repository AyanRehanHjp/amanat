/* check JWT token */
const token = localStorage.getItem("token");

if(!token){
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

    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    localStorage.removeItem("userName");
    localStorage.removeItem("firstName");
    localStorage.removeItem("lastName");

    window.location.href="/login.html";

}