document.getElementById("beneficiaryForm").addEventListener("submit", function(e){

    e.preventDefault();

    let data = {
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


    // 🔥 EMPTY FIELD CHECK
    for(let key in data){
        if(!data[key]){
            document.getElementById("msg").innerText = "All fields are mandatory";
            return;
        }
    }

    // 🔥 MOBILE VALIDATION
    if(data.mobile.length !== 10 || isNaN(data.mobile)){
        document.getElementById("msg").innerText = "Enter valid 10 digit mobile number";
        return;
    }


    // 🔥 API CALL
    fetch("http://localhost:9000/beneficiary/addBeneficiary",{
        method:"POST",
        headers:{
            "Content-Type":"application/json"
        },
        body: JSON.stringify(data)
    })
    .then(res => res.text())
    .then(msg => {

        alert(msg);

        document.getElementById("beneficiaryForm").reset();
        document.getElementById("msg").innerText = "";

    })
    .catch(err => {

        console.error(err);
        document.getElementById("msg").innerText = "Server error";

    });

});