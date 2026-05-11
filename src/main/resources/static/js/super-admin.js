function loadAdmins() {

    let table =
        document.getElementById(
            "adminTableBody"
        );

    table.innerHTML =

    `<tr>

        <td colspan="6"
            class="empty">

            Loading...

        </td>

    </tr>`;

    fetch(
        "http://localhost:9000/admins/getAllAdmins"
    )

    .then(res => res.json())

    .then(data => {

        if(data.length === 0){

            table.innerHTML =

            `<tr>

                <td colspan="6"
                    class="empty">

                    No admins found

                </td>

            </tr>`;

            return;
        }

        table.innerHTML = "";

        data.forEach(admin => {

            let status = "";

            if(admin.status === "PENDING"){

                status =

                `<span class="pending-status">

                    Wanted To Resign

                </span>`;
            }

            else if(
                admin.status === "RESIGNED"
            ){

                status =

                `<span class="accepted-status">

                    Resignation Accepted

                </span>`;
            }

            else{

                status =

                `<span class="active-status">

                    ACTIVE_ADMIN

                </span>`;
            }

            let action = `

                <select

                    ${
                        admin.status !== "PENDING"
                        ? "disabled"
                        : ""
                    }

                    onchange="
                        handleAction(
                            this.value,
                            ${admin.id}
                        )
                    "
                >

                    <option selected disabled>

                        REJECTED

                    </option>

                    <option value="ACCEPT">

                        ACCEPT

                    </option>

                </select>
            `;

            let row = `

                <tr>

                    <td>${admin.id}</td>

                    <td>${admin.userId}</td>

                    <td>${admin.fullName}</td>

                    <td>${admin.designation}</td>

                    <td>${status}</td>

                    <td>${action}</td>

                </tr>
            `;

            table.innerHTML += row;

        });

    })

    .catch(() => {

        table.innerHTML =

        `<tr>

            <td colspan="6"
                class="empty">

                Error loading data

            </td>

        </tr>`;
    });
}

/* BACK BUTTON */

function goBack(){

    window.location.href =
        "/super-admin.html";
}

/* CREATE ADMIN */

function goToCreate(){

    window.location.href =
        "/create-admin.html";
}

/* ACCEPT RESIGNATION */

function handleAction(value, id){

    if(value !== "ACCEPT"){
        return;
    }

    let yes = confirm(
        "Do you want to accept resignation?"
    );

    if(!yes){

        loadAdmins();

        return;
    }

    fetch(
        `/admins/accept-resignation/${id}`,
        {
            method:"POST"
        }
    )

    .then(res => res.text())

    .then(msg => {

        showPopup(msg);

        loadAdmins();
    })

    .catch(() => {

        showPopup(
            "Error while accepting resignation"
        );
    });
}

/* LOGOUT */

function logoutSuperAdmin(){

    localStorage.removeItem("token");

    localStorage.removeItem("role");

    localStorage.removeItem(
        "superAdminUsername"
    );

    window.location.href =
        "/admin-login.html";
}