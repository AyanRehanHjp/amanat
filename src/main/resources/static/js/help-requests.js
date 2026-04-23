// ================= LOAD DATA =================
function loadRequests(){

fetch("http://localhost:9000/beneficiary/allBeneficiaries")
.then(res => res.json())
.then(data => {

let table = document.querySelector("#helpTable tbody");
table.innerHTML = "";

data.forEach(req => {

let currentStatus = req.status || "PENDING";

let row = `
<tr id="row-${req.id}">

<td>${req.id}</td>
<td>${req.needyName}</td>
<td>${req.mobile}</td>
<td>${req.address}</td>
<td>${req.pinCode}</td>
<td>${req.state}</td>
<td>${req.problem}</td>
<td>${req.familyOccupation}</td>
<td>${req.financialCondition}</td>
<td>${req.comment}</td>

<td>
<select onchange="changeStatusColor(this)" class="status-dropdown ${currentStatus.toLowerCase()}">
    <option value="PENDING" ${currentStatus==='PENDING'?'selected':''}>PENDING</option>
    <option value="WORKING" ${currentStatus==='WORKING'?'selected':''}>WORKING</option>
    <option value="ACCEPTED" ${currentStatus==='ACCEPTED'?'selected':''}>ACCEPTED</option>
    <option value="REJECTED" ${currentStatus==='REJECTED'?'selected':''}>REJECTED</option>
    <option value="DONE" ${currentStatus==='DONE'?'selected':''}>DONE</option>
</select>
</td>

<td>
<input type="number" class="amount-input" id="amt-${req.id}" value="${req.amount || ''}" placeholder="₹">
</td>

<td>
<button class="save-btn" onclick="updateStatus(${req.id})">Save</button>
</td>

</tr>
`;

table.innerHTML += row;

});

});
}


// ================= CHANGE COLOR =================
function changeStatusColor(select){
select.className = "status-dropdown " + select.value.toLowerCase();
}


// ================= UPDATE STATUS =================
function updateStatus(id){

let row = document.getElementById("row-"+id);

let status = row.querySelector(".status-dropdown").value;
let amount = row.querySelector(".amount-input").value || 0;

fetch("http://localhost:9000/beneficiary/updateStatus/"+id,{
    method:"PUT",
    headers:{
        "Content-Type":"application/json"
    },
    body: JSON.stringify({
        status: status,
        amount: amount
    })
})
.then(res => res.text())
.then(msg => {
    alert(msg);
    loadRequests();
})
.catch(err=>{
    console.error(err);
    alert("Update failed");
});

}


// ================= INIT =================
window.onload = function(){
    loadRequests();
};