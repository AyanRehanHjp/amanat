
function resetPassword(){

    let userName =
    document.getElementById("userName")
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

    // USERNAME
    if(userName === ""){

        msgBox.style.color = "red";

        msgBox.innerText =
        "Username is required";

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

    // API CALL
    fetch(
        BASE_URL + "/signUp/forgotPassword",
        {

            method:"POST",

            headers:{
                "Content-Type":"application/json"
            },

            body:JSON.stringify({

                userName:userName,

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