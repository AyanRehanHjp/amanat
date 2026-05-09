function goBack(){

window.location.href="/admin.html";

}
function submitPayment(){

    // 🔥 Get values
    let name = document.getElementById("payeeName").value.trim();
    let memberId = document.getElementById("memberId").value.trim();
    let mobile = document.getElementById("mobile").value.trim();
    let amount = document.getElementById("amount").value.trim();
    let utr = document.getElementById("utr").value.trim();
    let paymentDate = document.getElementById("paymentDate").value;
    let comment = document.getElementById("comment").value.trim();

    // ===============================
    // 🔥 VALIDATION
    // ===============================
    if(!name || !mobile || !amount || !utr || !paymentDate){
        showPopup("Please fill all required fields");
        return;
    }
    if(name.length < 3){
        showPopup("Name must be minimum 3 characters");
        return;
    }

    if(!/^[a-zA-Z ]+$/.test(name)){
        showPopup("Name must contain letters only");
        return;
    }

    if(!/^[0-9]{10}$/.test(mobile)){
        showPopup("Mobile number must be exactly 10 digits");
        return;
    }

    if(isNaN(amount) || amount <= 1){
        showPopup("Enter valid amount");
        return;
    }

    if(utr.length !== 5){
        showPopup("UTR must be last 5 digits");
        return;
    }

    // ===============================
    // 🔥 DATA (DTO ke hisaab se keys match honi chahiye)
    // ===============================
    let data = {
        payeeName: name,
        memberId: memberId,
        mobile: mobile,
        amount: amount,
        payDate: paymentDate,
        utrNo: utr,
        comment: comment
    };

    console.log("Sending Data:", data);

    // ===============================
    // 🔥 API CALL
    // ===============================
    fetch("/scan&pay/addPayee", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(res => {
        if(!res.ok){
            throw new Error("API failed");
        }
        return res.text();  //
    })
    .then(msg => {

        showPopup(msg);

        // 🔥 reset form
        document.querySelectorAll(".payment-form input").forEach(input => input.value = "");

    })
    .catch(err => {
        console.error(err);
        showPopup("Error submitting payment");
    });
}