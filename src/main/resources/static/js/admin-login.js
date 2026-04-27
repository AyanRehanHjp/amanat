function adminLogin(){

    let adminId = document.getElementById("adminId").value;
    let password = document.getElementById("password").value;

    if(!adminId || !password){
        showMsg("Please enter Admin ID and Password");
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
        return res.text(); // token aa raha hai
    })
    .then(token => {

        // 🔥 token store karo
        localStorage.setItem("token", token);

        showMsg("Login Successful");

        // redirect after 1 sec
        setTimeout(() => {
            window.location.href = "/admin.html";
        }, 1000);
    })
    .catch(err => {
        showMsg(err.message || "Login Failed");
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