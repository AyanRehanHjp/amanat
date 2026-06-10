let receiptBlobUrl = "";
// Redirect
function goHome(){ window.location.href="/admin.html"; }

// Year dropdown
const yearSelect=document.getElementById("year");

for(let y=2021;y<=2099;y++){
    let option=document.createElement("option");
    option.value=y;
    option.text=y;
    yearSelect.appendChild(option);
}

yearSelect.value=new Date().getFullYear();

// FORM SUBMIT
document.getElementById("paymentForm").addEventListener("submit",function(e){

    e.preventDefault();

    let data={
        memberId:document.getElementById("memberId").value,
        amount:document.getElementById("amount").value,
        forMonth:document.getElementById("month").value,
        forYear:document.getElementById("year").value,
        comment:document.getElementById("comment").value
    };

    fetch("/incomeDet/addPayment",{
        method:"POST",
        headers:{"Content-Type":"application/json"},
        body:JSON.stringify(data)
    })

    .then(res=>res.text())

    .then(msg=>{

if(!msg.startsWith("AWTIN")){
            document.getElementById("popupMsg").innerText=msg;
            document.getElementById("popup").style.display="flex";
            return;
        }

        document.getElementById("popupMsg").innerText=msg;
        document.getElementById("popup").style.display="flex";

    const { jsPDF }=window.jspdf;
    const doc=new jsPDF();

    const logo=document.getElementById("trustLogo");

    doc.addImage(logo,"PNG",160,8,30,22);


    // TITLE
    doc.setFontSize(30);
    doc.setFont(undefined,"bold");
    doc.setTextColor(0,51,153);
    doc.text("AMANAT WELFARE TRUST",15,20);


    // SUBTITLE
    doc.setFontSize(20);
    doc.setFont(undefined,"bolditalic");
    doc.setTextColor(0,0,0);

    doc.text(
        "Hajipur, Bihar - 844101",
        105,
        32,
        { align: "center" }
    );

    // RECEIPT INFO RIGHT SIDE
    doc.setFontSize(12);
    doc.setFont(undefined,"bold");
    doc.setTextColor(0,0,0);


    // DATE
    const today=new Date();

    const dd=String(today.getDate()).padStart(2,'0');
    const mm=String(today.getMonth()+1).padStart(2,'0');
    const yyyy=today.getFullYear();

    const formattedDate=dd+"-"+mm+"-"+yyyy;

const receiptNo=msg;

    // RECEIPT NUMBER
    doc.text(
        "Receipt No : "+receiptNo,
        140,
        58
    );


    // PAYMENT DATE
    doc.text(
        "Payment Date : "+formattedDate,
        140,
        67
    );


    // RECEIPT BOX
    doc.setFillColor(0,51,153);

    doc.roundedRect(
        72,
        38,
        75,
        12,
        2,
        2,
        "F"
    );


    // RECEIPT TEXT
    doc.setFontSize(18);
    doc.setTextColor(255,255,255);

    doc.text(
        "RECEIPT",
        110,
        46,
        {align:"center"}
    );


    // TABLE
    doc.autoTable({

        startY:72,

        theme:"grid",

        head:[["Content","Details"]],

        body:[

            ["Member ID",data.memberId],

            ["Full Name",
            document.getElementById("fullName").value],

            ["Mobile Number",
            document.getElementById("mobile").value],

            ["Amount",
            "Rs. "+data.amount],

            ["Month",
            data.forMonth],

            ["Year",
            data.forYear],

            ["Comment",
            data.comment || "-"]

        ],

        styles:{
            fontSize:10
        },

        headStyles:{
            fillColor:[0,51,153]
        }

    });


    // TOTAL BOX
    let finalY=doc.lastAutoTable.finalY+15;

    doc.setFillColor(245,245,245);

    doc.rect(
        130,
        finalY,
        60,
        22,
        "F"
    );


    // TOTAL TEXT
    doc.setTextColor(0,0,0);

    doc.setFontSize(13);

    doc.text(
        "Total Amount",
        138,
        finalY+8
    );


    // TOTAL AMOUNT
    doc.setFontSize(16);

    doc.setFont(undefined,"bold");

    doc.text(
        "Rs. "+data.amount,
        145,
        finalY+17
    );


    // SUCCESS
    doc.setTextColor(0,128,0);

    doc.setFontSize(15);

    doc.text(
        "Your Payment Added Successfully",
        105,
        finalY+40,
        {align:"center"}
    );


    // THANK YOU
    doc.setTextColor(80);

    doc.setFontSize(12);

    doc.setFont(undefined,"normal");

    doc.text(
        "Thank you for your valuable contribution and support to Amanat Welfare Trust.",
        105,
        finalY+55,
        {align:"center"}
    );


    // FOOTER BOX
    doc.setFillColor(0,0,120);

    doc.rect(
        0,
        282,
        210,
        15,
        "F"
    );


    // FOOTER TEXT STYLE
    doc.setTextColor(255,255,255);

    doc.setFontSize(9);

    doc.setFont(undefined,"bold");


    // MOBILE
    doc.text(
        "Mob: +91 7277222729",
        10,
        291
    );


    // EMAIL
    doc.text(
        "Mail: amanatwelfaretrust@gmail.com",
        105,
        291,
        {align:"center"}
    );


    // WEBSITE
    doc.text(
        "Web: www.amanatwelfaretrust.com",
        200,
        291,
        {align:"right"}
    );
            // SHAREABLE URL
        receiptBlobUrl = doc.output("bloburl");

        // DOWNLOAD
        doc.save("AWT_Receipt.pdf");

        //Test
//        window.open(doc.output("bloburl"));
        // RESET
        document.getElementById("paymentForm").reset();

    })

    .catch(()=>{

        document.getElementById("popupMsg").innerText="Server Error";
        document.getElementById("popup").style.display="flex";

    });

});


// Close popup
function closePopup(){
    document.getElementById("popup").style.display="none";
}


// MEMBER SEARCH
document.getElementById("memberId").addEventListener("input",function(){

    let value=this.value;

    if(!value || value.length<2) return;

    fetch("/incomeDet/searchMember?value="+value)

    .then(res=>res.json())

    .then(data=>{

        let list=document.getElementById("memberList");

        list.innerHTML="";

        if(data.length===0){
            document.getElementById("msg").innerText="No record found";
            return;
        }

        document.getElementById("msg").innerText="";

        data.forEach(m=>{

            let option=document.createElement("option");

            option.value=m[0];
            option.label=m[0]+" - "+m[1]+" - "+m[2];

            list.appendChild(option);

        });

    });

});


// Autofill
document.getElementById("memberId").addEventListener("change",function(){

    let value=this.value;

    fetch("/incomeDet/searchMember?value="+value)

    .then(res=>res.json())

    .then(data=>{

        if(data.length>0){

            let m=data[0];

            document.getElementById("memberId").value=m[0];
            document.getElementById("fullName").value=m[1];
            document.getElementById("mobile").value=m[2];

        }

    });

});
async function shareWhatsapp(){

    const pdfBlob =
        await fetch(receiptBlobUrl).then(r => r.blob());

    const file = new File(
        [pdfBlob],
        "AWT_Receipt.pdf",
        { type:"application/pdf" }
    );

    if(navigator.canShare &&
        navigator.canShare({ files:[file] })){

        navigator.share({
            title:"AWT Receipt",
            text:"Payment Receipt",
            files:[file]
        });

    }else{

        let whatsappUrl =
            "https://wa.me/?text=Payment Receipt";

        window.open(whatsappUrl,"_blank");
    }
}
function loadPendingRequests(){
    showLoader();
    fetch("/scan&pay/allPayments")
    .then(response => response.json())
    .then(data => {

        let tbody =
        document.getElementById("pendingTableBody");

        tbody.innerHTML = "";

        data.forEach(item => {

            tbody.innerHTML += `
            <tr>
                <td>${item.id ?? ''}</td>
                <td>${item.payeeName ?? ''}</td>
                <td>${item.memberId ?? ''}</td>
                <td>${item.utrNo ?? ''}</td>
                <td>₹ ${item.amount ?? 0}</td>
                <td>${item.mobile ?? ''}</td>
                <td>${item.payDate ?? ''}</td>
                <td>${item.comment ?? ''}</td>
                <td>
                <select onchange="updateStatus(${item.id},this)"
                ${item.entryStatus === 'DONE' ? 'disabled' : ''}>
                <option value="PENDING" ${item.entryStatus === 'PENDING' ? 'selected' : ''}>Pending</option>
                <option value="DONE" ${item.entryStatus === 'DONE' ? 'selected' : ''}>Done</option>
                </select>
                </td>
            </tr>
            `;
        });
hideLoader();
        document.getElementById("pendingSection")
        .style.display = "block";
    })
    .catch(error => {
    hideLoader();
        console.error(error);
        alert("Unable to load data");
    });
}
function updateStatus(id,obj){

    if(obj.value !== "DONE"){
        return;
    }
    showLoader();
    fetch("/scan&pay/updateStatus/" + id + "?status=DONE",{
        method:"PUT",
        headers:{
            "Authorization":"Bearer " + token
        }
    })
    .then(res => {

        console.log("STATUS =", res.status);

        if(!res.ok){
            throw new Error("Request Failed : " + res.status);
        }

        return res.text();
    })
    .then(msg => {

        console.log("MSG =", msg);

        showPopup(msg);

        obj.disabled = true;
    })
    .catch(err => {
        console.error(err);

        showPopup("Error : " + err.message);
    })
    .finally(() => {

        hideLoader();

    });

}
function toggleStatusSearch(){

    let div =
    document.getElementById("statusSearchDiv");

    div.style.display =
    div.style.display === "none"
    ? "block"
    : "none";
}
function searchByStatus(){

    let status =
    document.getElementById("statusFilter").value;

    if(!status){
        showPopup("Select Status");
        return;
    }

    showLoader();

    fetch("/scan&pay/paymentsByStatus?status="+status,{
        headers:{
            "Authorization":"Bearer "+token
        }
    })
    .then(res=>res.json())
    .then(data=>{

        let tbody =
        document.getElementById("pendingTableBody");

        tbody.innerHTML="";

        data.forEach(item=>{

            tbody.innerHTML += `
            <tr>
                <td>${item.id ?? ''}</td>
                <td>${item.payeeName ?? ''}</td>
                <td>${item.memberId ?? ''}</td>
                <td>${item.utrNo ?? ''}</td>
                <td>₹ ${item.amount ?? 0}</td>
                <td>${item.mobile ?? ''}</td>
                <td>${item.payDate ?? ''}</td>
                <td>${item.comment ?? ''}</td>
<td>
<select onchange="updateStatus(${item.id},this)"
${item.entryStatus === 'DONE' ? 'disabled' : ''}>

<option value="PENDING" ${item.entryStatus === 'PENDING' ? 'selected' : ''}>Pending</option>

<option value="DONE" ${item.entryStatus === 'DONE' ? 'selected' : ''}>Done</option>

</select>
</td>            </tr>
            `;
        });

        document.getElementById("pendingSection")
        .style.display="block";
    })
    .finally(()=>{
        hideLoader();
    });
}
function resetTable(){

    document.getElementById("statusFilter").value="";

    document.getElementById("pendingTableBody")
    .innerHTML="";

    document.getElementById("pendingSection")
    .style.display="none";
}