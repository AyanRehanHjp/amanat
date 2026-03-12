function generateReceipt(){

let name=document.getElementById("name").value;
let address=document.getElementById("address").value;
let amount=document.getElementById("amount").value;
let date=document.getElementById("date").value;
let receiptNo=document.getElementById("receiptNo").value;

fetch("/recpdfgen/generateReceipt",{
method:"POST",
headers:{
"Content-Type":"application/json"
},
body:JSON.stringify({
receiptNo:receiptNo,
name:name,
address:address,
amount:amount,
recDate:date
})
})
.then(res=>{

if(res.status==409){
alert("Receipt already generated");
return;
}

alert("Receipt Created Successfully");

document.getElementById("pName").innerText=name;
document.getElementById("pAddress").innerText=address;
document.getElementById("pDate").innerText=date;
document.getElementById("pRec").innerText=receiptNo;
document.getElementById("pAmount").innerText="Rs "+amount;

document.getElementById("receiptModal").style.display="flex";

});

}

function download(){

let receiptNo=document.getElementById("receiptNo").value;

window.location="/recpdfgen/downloadReceipt/"+receiptNo;

}
function closeModal(){
document.getElementById("receiptModal").style.display="none";
}