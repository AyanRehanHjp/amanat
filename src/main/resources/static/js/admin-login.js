function adminLogin(){

    let adminId = document.getElementById("adminId").value;
    let password = document.getElementById("password").value;

    if(!adminId || !password){
        showPopup("Please enter Admin ID and Password");
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
            password: password
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