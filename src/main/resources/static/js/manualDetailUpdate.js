function openPage(url){
    window.location.href = url;
}

function feature(){
    alert("Feature Coming Soon");
}

function logout(){

    let confirmLogout = confirm("Are you sure you want to logout ?");

    if(confirmLogout){

        localStorage.clear();

        window.location.href = "/admin-login.html";
    }
}


/* =========================
   SECURITY CHECK
========================= */

function checkAdminAccess(){

    let token = localStorage.getItem("token");
    let role = localStorage.getItem("role");

    if(!token || role !== "ADMIN"){

        alert("Access Denied - Admin Only");

        window.location.href = "/admin-login.html";
    }
}


/* =========================
   COMMON MESSAGE
========================= */

function showMessage(message){

    alert(message);
}


/* =========================
   COMMON CONFIRMATION
========================= */

function confirmAction(message){

    return confirm(message);
}


/* =========================
   MANUAL OPERATIONS
========================= */

function manualUserVerify(){

    if(confirmAction("Open Manual User Verify Page ?")){

        openPage('/manual-user-verify.html');
    }
}

function manualBillGenerate(){

    if(confirmAction("Open Manual Bill Generate Page ?")){

        openPage('/manual-bill-generate.html');
    }
}

function manualPaymentUpdate(){

    if(confirmAction("Open Manual Payment Update Page ?")){

        openPage('/manual-payment-update.html');
    }
}

function manualStatusChange(){

    if(confirmAction("Open Manual Status Change Page ?")){

        openPage('/manual-status-change.html');
    }
}

function manualDisconnect(){

    if(confirmAction("Open Manual Disconnect Page ?")){

        openPage('/manual-disconnect.html');
    }
}

function manualReconnect(){

    if(confirmAction("Open Manual Reconnect Page ?")){

        openPage('/manual-reconnect.html');
    }
}

function manualReceiptGenerate(){

    if(confirmAction("Open Manual Receipt Generate Page ?")){

        openPage('/manual-receipt.html');
    }
}

function manualReportGenerate(){

    if(confirmAction("Open Manual Report Generate Page ?")){

        openPage('/manual-report.html');
    }
}

function manualDataSync(){

    if(confirmAction("Start Manual Data Sync ?")){

        showMessage("Manual Data Sync Started");
    }
}

function clearSystemCache(){

    if(confirmAction("Do you want to clear system cache ?")){

        showMessage("System Cache Cleared Successfully");
    }
}

function manualFileUpload(){

    if(confirmAction("Open Manual File Upload Page ?")){

        openPage('/manual-file-upload.html');
    }
}

function sendNotification(){

    if(confirmAction("Open Notification Panel ?")){

        openPage('/manual-notification.html');
    }
}

function databaseBackup(){

    if(confirmAction("Start Database Backup ?")){

        showMessage("Database Backup Started");
    }
}

function restoreDatabase(){

    if(confirmAction("Restore Database Backup ?")){

        showMessage("Database Restore Started");
    }
}


/* =========================
   PAGE LOAD
========================= */

window.onload = function(){

    checkAdminAccess();

    fetch('/common.html')
    .then(res => res.text())
    .then(data => {

        document.getElementById("common-navbar").innerHTML = data;
    });
};