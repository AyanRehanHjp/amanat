function generateReceipt(){

let name=document.getElementById("name").value;
let address=document.getElementById("address").value;
let amount=document.getElementById("amount").value;
let date=document.getElementById("date").value;
let receiptNo=document.getElementById("receiptNo").value;
if(!receiptNo){
alert("Receipt Number is required");
return;
}

if(!/^[0-9]+$/.test(receiptNo)){
alert("Receipt Number must be numeric");
return;
}

if(!name){
alert("Name is required");
return;
}

if(!amount){
alert("Amount is required");
return;
}

if(!date){
alert("Date is required");
return;
}
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
showPopup("Receipt already generated");
return;
}

showPopup("Receipt Created Successfully");

document.getElementById("pName").innerText=name;
document.getElementById("pAddress").innerText=address;
let d = new Date(date);
document.getElementById("pDate").innerText =d.getDate() + "-" + (d.getMonth()+1) + "-" + d.getFullYear();
document.getElementById("pRec").innerText=receiptNo;
document.getElementById("pAmount").innerText=amount+"/-";

let words = numberToWords(parseInt(amount));
document.getElementById("pAmountWords").innerText = words + " Rupees Only";

document.getElementById("receiptModal").style.display="flex";

});

}

function closeModal(){
document.getElementById("receiptModal").style.display="none";
}

function download(){
let receiptNo = document.getElementById("receiptNo").value;
const element = document.getElementById("receiptArea");

html2canvas(element,{useCORS:true,scale:2}).then(function(canvas){

const imgData = canvas.toDataURL("image/png");

const pdf = new jsPDF('landscape','px',[canvas.width,canvas.height]);

pdf.addImage(imgData,'PNG',0,0,canvas.width,canvas.height);
let pdfBlob = pdf.output("blob")

let reader = new FileReader()

reader.readAsDataURL(pdfBlob)

reader.onloadend = function(){

    let base64data = reader.result.split(',')[1]

    fetch("/recpdfgen/savePdf",{

        method:"POST",

        headers:{
            "Content-Type":"application/json"
        },

        body:JSON.stringify({
            receiptNo:receiptNo,
            pdf:base64data
        })
    })
}
pdf.save(receiptNo+".pdf");

});

}

function goAdmin(){
window.location="/admin.html";
}

function numberToWords(num){

const ones=["","One","Two","Three","Four","Five","Six","Seven","Eight","Nine"];
const tens=["","","Twenty","Thirty","Forty","Fifty","Sixty","Seventy","Eighty","Ninety"];
const teens=["Ten","Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen"];

if(num==0) return "";

if(num<10) return ones[num];

if(num<20) return teens[num-10];

if(num<100){
return tens[Math.floor(num/10)]+" "+ones[num%10];
}

if(num<1000){
return ones[Math.floor(num/100)]+" Hundred "+numberToWords(num%100);
}

if(num<100000){
let thousands = numberToWords(Math.floor(num/1000))+" Thousand";
let rest = numberToWords(num%1000);
return rest ? thousands+" "+rest : thousands;
}

return num;
}
