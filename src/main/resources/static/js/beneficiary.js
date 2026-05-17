let isSubmitting = false;

document.getElementById("beneficiaryForm").addEventListener("submit", function(e){

    e.preventDefault();

    if(isSubmitting){
        return;
    }

    const submitBtn = document.querySelector("#beneficiaryForm button[type='submit']");

    // Collect JSON data
    let beneficiaryData = {
        needyName: document.getElementById("needyName").value.trim(),
        mobile: document.getElementById("mobile").value.trim(),
        address: document.getElementById("address").value.trim(),
        pinCode: document.getElementById("pinCode").value.trim(),
        state: document.getElementById("state").value.trim(),
        problem: document.getElementById("problem").value.trim(),
        familyOccupation: document.getElementById("familyOccupation").value.trim(),
        financialCondition: document.getElementById("financialCondition").value.trim(),
        comment: document.getElementById("comment").value.trim()
    };

    // Empty field check
    for(let key in beneficiaryData){
        if(!beneficiaryData[key]){
            document.getElementById("msg").innerText = "All fields are mandatory";
            return;
        }
    }

    // Mobile validation
    if(beneficiaryData.mobile.length !== 10 || isNaN(beneficiaryData.mobile)){
        document.getElementById("msg").innerText = "Enter valid 10 digit mobile number";
        return;
    }

    isSubmitting = true;
    submitBtn.disabled = true;
    submitBtn.innerText = "Submitting...";

    // Prepare FormData for JSON + File
    let formData = new FormData();

    formData.append(
        "beneficiary",
        new Blob(
            [JSON.stringify(beneficiaryData)],
            { type: "application/json" }
        )
    );

    let fileInput = document.getElementById("supportiveDocuments");

    if(fileInput && fileInput.files.length > 0){
        formData.append("file", fileInput.files[0]);
    }

    // API call
    fetch(ADD_BENEFICIARY,{
        method:"POST",
        body:formData
    })
    .then(res => res.text())
    .then(msg => {

        showPopup(msg);

        document.getElementById("beneficiaryForm").reset();

        document.getElementById("msg").innerText = "";

        isSubmitting = false;

        submitBtn.disabled = false;

        submitBtn.innerText = "Submit Request";
    })
    .catch(err => {

        console.error(err);

        document.getElementById("msg").innerText = "Server error";

        isSubmitting = false;

        submitBtn.disabled = false;

        submitBtn.innerText = "Submit Request";
    });

});