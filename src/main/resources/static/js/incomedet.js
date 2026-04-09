function goHome(){
    window.location.href="/admin.html";
}


const yearSelect = document.getElementById("year");

for(let y = 2021; y <= 2099; y++){

    let option = document.createElement("option");

    option.value = y;
    option.text = y;

    yearSelect.appendChild(option);
}

yearSelect.value = new Date().getFullYear();



document.getElementById("paymentForm").addEventListener("submit",function(e){

    e.preventDefault();

    let data = {

        memberId: document.getElementById("memberId").value,
        amount: document.getElementById("amount").value,
        forMonth: document.getElementById("month").value,
        forYear: document.getElementById("year").value
//        forYear: document.getElementById("year").value


    };


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

    })

    .catch(()=>{

        document.getElementById("popupMsg").innerText = "Server Error";

        document.getElementById("popup").style.display = "flex";

    });

});



function closePopup(){

    document.getElementById("popup").style.display = "none";

}
const dataList = document.getElementById("memberList");

for (let i = 1; i <= 1000; i++) {
    let option = document.createElement("option");
    option.value = "AWT" + String(i).padStart(3, '00');
    dataList.appendChild(option);
}
