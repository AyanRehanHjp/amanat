function loadAdmins() {

    let table = document.getElementById("adminTableBody");
    table.innerHTML = `<tr><td colspan="4" class="empty">Loading...</td></tr>`;

    fetch("http://localhost:9000/admins/getAllAdmins")
    .then(res => res.json())
    .then(data => {

        if (data.length === 0) {
            table.innerHTML = `<tr><td colspan="4" class="empty">No admins found</td></tr>`;
            return;
        }

        table.innerHTML = "";

        data.forEach(admin => {
            let row = `
                <tr>
                    <td>${admin.id}</td>
                    <td>${admin.userId}</td>
                    <td>${admin.fullName}</td>
                    <td>${admin.designation}</td>
                </tr>
            `;
            table.innerHTML += row;
        });

    })
    .catch(() => {
        table.innerHTML = `<tr><td colspan="4" class="empty">Error loading data</td></tr>`;
    });
}

/* NAVIGATION */
function goBack() {
    window.location.href = "/admin.html";
}

function goToCreate() {
    window.location.href = "/create-admin.html";
}