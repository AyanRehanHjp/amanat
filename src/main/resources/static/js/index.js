function goToLogin(){
window.location.href="login.html";
}

function goToSignup(){
window.location.href="signup.html";
}

function featureWorking(){
showPopup("Sorry, this feature is under development.");
}
function goToAdmin(){
    window.location.href="/admin.html";
}
function goToAdminLogin(){
    window.location.href="/admin-login.html";
}
function instagram(){
    window.open(URLS.INSTAGRAM, "_blank");
}
function youtube(){
    window.open(URLS.YOUTUBE, "_blank");
}

function trackStatus() {
    let token = document.getElementById("tokenInput").value.trim();

    if (!token) {
        showPopup("Enter token first");
        return;
    }


    fetch(TRACK_BENEFICIARY_STATUS  + token)
            .then(async res => {
                let response = await res.text();
                if (!res.ok) {
                    throw new Error(response);
                }
                return JSON.parse(response);
            })
        .then(data => {
            document.getElementById("resultBox").style.display = "block";
            document.getElementById("resultBox").innerHTML = `
                <div class="result-card success">
                    <span class="close-btn" onclick="closeResult()">✖</span>
                    <h3>Status: ${data.status.toUpperCase()}</h3>
                    <p><b>Name:</b> ${data.needyName}</p>
                    <p><b>Amount:</b> ${data.amount ?? "N/A"}</p>
                </div>
            `;
        })
.catch(error => {
    document.getElementById("resultBox").style.display = "block";
    document.getElementById("resultBox").innerHTML = `
        <div class="result-card error">
            <span class="close-btn" onclick="closeResult()">✖</span>
            <h3 style="color:red;">${error.message}</h3>
        </div>
    `;
});
}

/* OUTSIDE function */
function closeResult() {
        document.getElementById("resultBox").innerHTML = "";
        document.getElementById("resultBox").style.display = "none";
}

function loadHealthBar() {

    Promise.all([
        fetch("/actuator/health").then(r => r.json()),
        fetch("/actuator/metrics/jvm.memory.used").then(r => r.json())
    ])
    .then(([health, memory]) => {

        const memoryMb =
            (memory.measurements[0].value / 1024 / 1024).toFixed(2);

        const freeDisk =
            (health.components.diskSpace.details.free /
            1024 / 1024 / 1024).toFixed(2);

        document.getElementById("healthBar").innerHTML = `
            🟢 System Healthy
            &nbsp; | &nbsp;
            🟢 DB Connected
            &nbsp; | &nbsp;
            💾 ${freeDisk} GB Free
            &nbsp; | &nbsp;
            🧠 ${memoryMb} MB RAM
        `;
    })
    .catch(() => {
        document.getElementById("healthBar").innerHTML =
            "🔴 System Health Unavailable";
    });
}
loadHealthBar();