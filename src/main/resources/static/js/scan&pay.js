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

    // ===============================
    // 🔥 VALIDATION
    // ===============================
    if(!name || !mobile || !amount || !utr || !paymentDate){
        alert("Please fill all required fields");
        return;
    }

    if(mobile.length !== 10){
        alert("Mobile number must be 10 digits");
        return;
    }

    if(isNaN(amount) || amount <= 0){
        alert("Enter valid amount");
        return;
    }

    if(utr.length !== 5){
        alert("UTR must be last 5 digits");
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
        payDate: paymentDate,   // ⚠️ backend me payDate hai
        utrNo: utr              // ⚠️ backend me utrNo hai
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
        return res.text();  // ⚠️ backend String return kar raha hai
    })
    .then(msg => {

        alert(msg); // ✅ "Payment details submitted Successfully"

        // 🔥 reset form
        document.querySelectorAll(".payment-form input").forEach(input => input.value = "");

    })
    .catch(err => {
        console.error(err);
        alert("Error submitting payment");
    });
}