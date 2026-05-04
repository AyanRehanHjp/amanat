document.getElementById("feedbackForm").addEventListener("submit", function(e){
    e.preventDefault();

    let fullName = document.getElementById("fullName").value.trim();
    let mobile = document.getElementById("mobile").value.trim();
    let feedback = document.getElementById("feedback").value.trim();

    // Empty validation
    if(!fullName || !mobile || !feedback){
        showPopup("All fields are required");
        return;
    }

    // Name validation
    if(fullName.length > 30){
        showPopup("Name should not exceed 30 characters");
        return;
    }

    if(!/^[A-Za-z ]+$/.test(fullName)){
        showPopup("Name should contain only alphabets");
        return;
    }

    // Mobile validation
    if(!/^[0-9]{10}$/.test(mobile)){
        showPopup("Mobile number must be exactly 10 digits");
        return;
    }

    // Feedback validation
    if(feedback.length > 500){
        showPopup("Feedback should not exceed 500 characters");
        return;
    }

    let data = {
        fullName: fullName,
        mobile: mobile,
        feedback: feedback
    };

    fetch("http://localhost:9000/feedback/addFeedback", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(res => {
        if(!res.ok){
            return res.text().then(msg => { throw new Error(msg); });
        }
        return res.text();
    })
    .then(msg => {
        showPopup(msg);
        document.getElementById("feedbackForm").reset();
    })
    .catch(err => {
        showPopup(err.message);
    });
});