function superAdminLogin(){

    let username =
        document.getElementById("username").value;

    let password =
        document.getElementById("password").value;

    if(!username || !password){

        showPopup(
            "Please enter Username and Password"
        );

        return;
    }

    // loader show
    document.getElementById("loader")
        .style.display = "flex";

    fetch("/super-admin/super-admin-login", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({

            username: username,
            password: password
        })
    })

    .then(res => {

        if(!res.ok){
            throw new Error(
                "Invalid Credentials"
            );
        }

        return res.json();
    })

    .then(data => {

        // token store
        localStorage.setItem(
            "token",
            data.token
        );

        // role store
        localStorage.setItem(
            "role",
            data.role
        );

        // username store
        localStorage.setItem(
            "superAdminUsername",
            username
        );

        showPopup(
            "Super Admin Login Successful"
        );

        window.redirectAfterPopup =
            "/super-admin.html";
    })

    .catch(err => {

        showPopup(
            err.message || "Login Failed"
        );
    })

    .finally(() => {

        document.getElementById("loader")
            .style.display = "none";
    });
}

function showMsg(msg){

    let box = document.getElementById("msg");

    box.innerText = msg;

    box.style.display = "block";
}