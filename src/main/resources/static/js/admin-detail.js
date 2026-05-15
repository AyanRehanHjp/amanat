let selectedAdminId = null;

function loadAdmins() {

    let table = document.getElementById("adminTableBody");
    table.innerHTML = `<tr><td colspan="5" class="empty">Loading...</td></tr>`;

    let loggedInUserId = localStorage.getItem("userId");

    fetch(GET_ALL_ADMINS)
    .then(res => res.json())
    .then(data => {

        if (data.length === 0) {
            table.innerHTML = `<tr><td colspan="5" class="empty">No admins found</td></tr>`;
            return;
        }

        table.innerHTML = "";

        data.forEach(admin => {

            let action = "";

            // 🔹 If pending show yellow text
            if(admin.status === "PENDING"){

                action = `
                    <span style="
                        color:#ff9800;
                        font-weight:bold;
                    ">
                        Resignation Pending
                    </span>
                `;
            }

            // 🔹 Only logged in admin button clickable
            else if(loggedInUserId === admin.userId){

                action = `
                    <button
                        onclick="showResignPopup(${admin.id})"
                        style="
                            background:red;
                            color:white;
                            font-weight:bold;
                        ">
                        RESIGN
                    </button>
                `;
            }

            // 🔹 Others disabled
            else{

                action = `
                    <button disabled
                        style="
                            background:#ccc;
                            color:white;
                            cursor:not-allowed;
                        ">
                        RESIGN
                    </button>
                `;
            }

            let row = `
                <tr>
                    <td>${admin.id}</td>
                    <td>${admin.userId}</td>
                    <td>${admin.fullName}</td>
                    <td>${admin.designation}</td>
                    <td>${action}</td>
                </tr>
            `;

            table.innerHTML += row;
        });

    })
    .catch(() => {
        table.innerHTML = `<tr><td colspan="5" class="empty">Error loading data</td></tr>`;
    });
}

/* NAVIGATION */
function goBack() {
    window.location.href = "/admin.html";
}


function resignAdmin(id){

    if(!confirm("Are you sure?")) return;

    fetch(`/admins/resign/${id}`, {
        method: "POST"
    })
    .then(res => res.text())
    .then(msg => {
        alert("Request sent for approval");
        loadAdmins(); // refresh
    })
    .catch(() => alert("Error"));
}

function showResignPopup(id){

    selectedAdminId = id;

    document.getElementById("resignPopup").style.display = "flex";
}

function closeResignPopup(){

    document.getElementById("resignPopup").style.display = "none";
}

function confirmResign(){

    fetch(`/admins/resign/${selectedAdminId}`, {
        method: "POST"
    })
    .then(res => res.text())
    .then(msg => {

        closeResignPopup();

        showPopup(
            "Your Request sent for approval.\n\n After Successful Approval you can't login again."
        );

        loadAdmins();
    })
    .catch(() => alert("Error"));
}
function supAdmLogin() {
    window.location.href = "/super-admin-login.html";
}