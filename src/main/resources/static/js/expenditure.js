fetch("http://localhost:9000/expenditure/allExpenditure")
.then(response => response.json())
.then(data => {

let table = document.querySelector("#expTable tbody");

data.forEach(exp => {

let date = new Date(exp.expDate).toLocaleDateString();

let row = `
<tr>
<td>${exp.id}</td>
<td>${exp.receiptNo}</td>
<td>${exp.name}</td>
<td>${exp.address}</td>
<td class="amount">₹ ${exp.amount}</td>
<td>${date}</td>
<td>${exp.year}</td>
</tr>
`;

table.innerHTML += row;

});
addTotalRow();
});

function goBack(){
window.location.href="/admin.html";
}

// auto current year
window.onload = function(){
document.getElementById("year").value = new Date().getFullYear();
}

function saveExp(){

let data = {
name: document.getElementById("name").value,
address: document.getElementById("address").value,
amount: document.getElementById("amount").value,
expDate: document.getElementById("expDate").value,
year: document.getElementById("year").value,
receiptNo: document.getElementById("receiptNo").value
};

fetch("http://localhost:9000/expenditure/addExpenditure",{
method:"POST",
headers:{
"Content-Type":"application/json"
},
body: JSON.stringify(data)
})
.then(res=>res.text())
.then(msg=>{
alert(msg);
location.reload(); // save ke baad table refresh
});

}
function addTotalRow() {
    let rows = document.querySelectorAll("#expTable tbody tr");
    let total = 0;

    rows.forEach(row => {
        let amountText = row.querySelector(".amount").innerText.replace("₹", "").trim();
        total += parseFloat(amountText) || 0;
    });

    let tbody = document.querySelector("#expTable tbody");

    let tr = document.createElement("tr");
    tr.classList.add("total-row");
    tr.innerHTML = `
        <td colspan="4"></td>
        <td><b>Total</b></td>
<td><b>₹ ${total.toLocaleString('en-IN')}</b></td>
        <td></td>
    `;

    tbody.appendChild(tr);
}