function showLoader(){
const loader=document.getElementById("loader");
if(loader){
loader.style.display="flex";
}
}

function hideLoader(){
const loader=document.getElementById("loader");
if(loader){
loader.style.display="none";
}
}

function featureWorking(){
    alert("Sorry, this feature is under development.");
}
function instagram(){
    window.open(URLS.INSTAGRAM, "_blank");
}
function youtube(){
    window.open(URLS.YOUTUBE, "_blank");
}
// 🔹 GLOBAL POPUP
function showPopup(message){

    const popup = document.getElementById("globalPopup");
    const msg = document.getElementById("globalPopupMsg");

    if(!popup || !msg){
        console.log("Popup not loaded yet");
        return;
    }

    msg.innerText = message;
    popup.style.display = "flex";
}

function closeGlobalPopup(){
    const popup = document.getElementById("globalPopup");
    if(popup){
        popup.style.display = "none";
    }

    if(window.redirectAfterPopup){
        window.location.href = window.redirectAfterPopup;
        window.redirectAfterPopup = null;
    }
}
function paymentNotAllowed(){
    showPopup("⚠ Payment not allowed without Sign Up.\n\n For security reasons, please Sign Up or Log In to continue.");
}