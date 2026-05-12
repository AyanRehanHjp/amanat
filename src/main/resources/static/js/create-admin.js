function createAdmin(){

    // GET VALUES
    let fullName =
    document.getElementById("fullName")
    .value
    .trim();

    let designation =
    document.getElementById("designation")
    .value
    .trim();

    let userId =
    document.getElementById("userId")
    .value
    .trim()
    .toUpperCase();

    let password =
    document.getElementById("password")
    .value
    .trim();


    // EMPTY VALIDATION
    if(
        !fullName ||
        !designation ||
        !userId ||
        !password
    ){

        showMsg(
        "All fields are required",
        false
        );

        return;
    }


    // FULL NAME VALIDATION
    if(
    !/^[A-Za-z ]+$/
    .test(fullName)
    ){

        showMsg(
        "Full Name should contain only alphabets",
        false
        );

        return;
    }

    if(fullName.length < 3){

        showMsg(
        "Full Name must be minimum 3 characters",
        false
        );

        return;
    }


    // DESIGNATION VALIDATION
    if(
    !/^[A-Za-z ]+$/
    .test(designation)
    ){

        showMsg(
        "Designation should contain only alphabets",
        false
        );

        return;
    }

    if(designation.length < 3){

        showMsg(
        "Designation must be minimum 3 characters",
        false
        );

        return;
    }


    // ADMIN ID VALIDATION
    if(
    !/^[A-Za-z]{3}[0-9]{3}$/
    .test(userId)
    ){

        showMsg(
        "Admin ID must be like ADM001",
        false
        );

        return;
    }


    // PASSWORD VALIDATION
    if(
    !/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&]).{8,24}$/
    .test(password)
    ){

        showMsg(
        "Password must contain uppercase, lowercase, number and special character",
        false
        );

        return;
    }


    // REQUEST DATA
    let data = {

        fullName: fullName,

        designation: designation,

        userId: userId,

        password: password

    };


    // API CALL
    fetch("/admins/create", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(data)

    })


    // RESPONSE
    .then(res => {

        if(!res.ok){

            return res.text()
            .then(msg => {

                throw new Error(msg);

            });
        }

        return res.text();

    })


    // SUCCESS
    .then(msg => {

        showMsg(
        msg,
        true
        );

        document
        .getElementById("adminForm")
        .reset();

    })


    // ERROR
    .catch(err => {

        showMsg(
        err.message,
        false
        );

    });

}



// POPUP MESSAGE
function showMsg(msg, isSuccess){

    let popup =
    document.getElementById("popup");

    let content =
    popup.querySelector(".popup-content");

    let title =
    document.getElementById("popupTitle");

    let subTitle =
    document.getElementById("popupSubTitle");

    let text =
    document.getElementById("popupMsg");


    // MESSAGE
    text.innerText = msg;


    // SUCCESS
    if(isSuccess){

        title.innerText =
        "🎉 Congratulations!";

        subTitle.innerText =
        "New Admin Created Successfully";

        content.style.background =
        "#28a745";

    }


    // ERROR
    else{

        title.innerText =
        "❌ Error";

        subTitle.innerText = "";

        content.style.background =
        "#dc3545";

    }


    // SHOW POPUP
    popup.style.display = "flex";


    // AUTO CLOSE
    setTimeout(() => {

        popup.style.display = "none";

    }, 5000);

}



// CLOSE BUTTON
function closePopup(){

    document.getElementById("popup")
    .style.display = "none";

}



// FORM SUBMIT
document
.getElementById("adminForm")
.addEventListener("submit", function(e){

    e.preventDefault();

    createAdmin();

});