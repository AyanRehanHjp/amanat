function openPage(url){
window.location.href=url;
}

function feature(){
alert("Feature coming soon");
}

function logout(){
 localStorage.clear();   // 🔥 token delete
    window.location.href="/admin-login.html";
    }

