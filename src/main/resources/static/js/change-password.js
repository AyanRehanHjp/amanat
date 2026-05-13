const BASE_URL = "http://localhost:9000";

function changePassword(){

    let currentPassword =
    document.getElementById("currentPassword")
    .value
    .trim();

    let newPassword =
    document.getElementById("newPassword")
    .value
    .trim();

    let confirmPassword =
    document.getElementById("confirmPassword")
    .value
    .trim();

    let msgBox =
    document.getElementById("msgBox");

    msgBox.innerText = "";

    // EMPTY CHECK
    if(
        currentPassword === "" ||
        newPassword === "" ||
        confirmPassword === ""
    ){

        msgBox.style.color = "red";

        msgBox.innerText =
        "All fields are required";

        return;
    }

    // PASSWORD REGEX
    let passwordPattern =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&]).{8,24}$/;

    if(
        !passwordPattern.test(newPassword)
    ){

        msgBox.style.color = "red";

        msgBox.innerText =
        "Password must contain uppercase, lowercase, number and special character";

        return;
    }

    // MATCH CHECK
    if(newPassword !== confirmPassword){

        msgBox.style.color = "red";

        msgBox.innerText =
        "Passwords do not match";

        return;
    }

    // TOKEN
    let token =
    localStorage.getItem("token");

    // API CALL
    fetch(
        BASE_URL + "/signUp/changePassword",
        {

            method:"POST",

            headers:{
                "Content-Type":"application/json",
                "Authorization":"Bearer " + token
            },

            body:JSON.stringify({

                currentPassword:currentPassword,

                newPassword:newPassword

            })

        }
    )

    .then(async res => {

        let response =
        await res.text();

        if(!res.ok){

            throw new Error(response);

        }

        return response;

    })

    .then(data => {

        msgBox.style.color =
        "green";

        msgBox.innerText =
        data;

        setTimeout(()=>{

            window.location.href =
            "/login.html";

        },2000);

    })

    .catch(err => {

        msgBox.style.color =
        "red";

        msgBox.innerText =
        err.message;

    });

}

function togglePassword(id){

    let input =
    document.getElementById(id);

    if(input.type === "password"){

        input.type = "text";

    }else{

        input.type = "password";

    }

}