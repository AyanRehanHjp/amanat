// 🔹 Redirect to home
function goHome(){
    window.location.href="/admin.html";
}


// 🔹 Year dropdown fill (2021–2099)
const yearSelect = document.getElementById("year");

for(let y = 2021; y <= 2099; y++){

    let option = document.createElement("option");

    option.value = y;
    option.text = y;

    yearSelect.appendChild(option);
}

// 🔹 Default current year select
yearSelect.value = new Date().getFullYear();


// 🔹 FORM SUBMIT (Save Payment)
document.getElementById("paymentForm").addEventListener("submit",function(e){

    e.preventDefault();

    let data = {

        memberId: document.getElementById("memberId").value,
        amount: document.getElementById("amount").value,
        forMonth: document.getElementById("month").value,
        forYear: document.getElementById("year").value,
        comment: document.getElementById("comment").value

    };

    // 🔹 API call to save payment
    fetch("/incomeDet/addPayment",{

        method:"POST",

        headers:{
            "Content-Type":"application/json"
        },

        body: JSON.stringify(data)

    })

    .then(res => res.text())

    .then(msg => {

        document.getElementById("popupMsg").innerText = msg;
        document.getElementById("popup").style.display = "flex";
        document.getElementById("paymentForm").reset();

    })

    .catch(()=>{

        document.getElementById("popupMsg").innerText = "Server Error";
        document.getElementById("popup").style.display = "flex";

    });

});


// 🔹 Close popup
function closePopup(){
    document.getElementById("popup").style.display = "none";
}


// 🔥 🔥 MEMBER SEARCH (ONLY MEMBER ID FIELD) 🔥 🔥


// 🔹 Typing → search API call
document.getElementById("memberId").addEventListener("input", function(){

    let value = this.value;

    if(!value || value.length < 2) return;

    fetch("/incomeDet/searchMember?value=" + value)
    .then(res => res.json())
    .then(data => {

        let list = document.getElementById("memberList");
        list.innerHTML = "";

        // ❌ No data
        if(data.length === 0){
            document.getElementById("msg").innerText = "No record found";
            return;
        }

        // ✅ Clear message
        document.getElementById("msg").innerText = "";

        // 🔽 Fill dropdown
        data.forEach(m => {

            let option = document.createElement("option");

            // value → memberId
            option.value = m[0];

            // label → full display
            option.label = m[0] + " - " + m[1] + " - " + m[2];

            list.appendChild(option);
        });

    });

});


// 🔹 Select from dropdown → autofill
document.getElementById("memberId").addEventListener("change", function(){

    let value = this.value;

    fetch("/incomeDet/searchMember?value=" + value)
    .then(res => res.json())
    .then(data => {

        if(data.length > 0){

            let m = data[0];

            document.getElementById("memberId").value = m[0];
            document.getElementById("fullName").value = m[1];
            document.getElementById("mobile").value = m[2];

        }

    });

});