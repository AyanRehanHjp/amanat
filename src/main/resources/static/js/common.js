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