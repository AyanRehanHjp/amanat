// ================= LOAD DATA =================
function loadRequests() {

    fetch(ALL_BENEFICIARY)
        .then(res => res.json())
        .then(data => {

            let table = document.querySelector("#helpTable tbody");
            table.innerHTML = "";

            data.forEach(req => {

                let currentStatus = req.status || "PENDING";
                let fileLink = req.supportiveDocuments || "";
                let amountValue =
                    currentStatus === "REJECTED"
                        ? "0.00"
                        : (req.amount || "");

                // ✅ PDF INLINE VIEW
                let viewLink = fileLink
                    ? fileLink.replace("/upload/", "/upload/fl_inline/")
                    : "";

                let row = `
                <tr id="row-${req.id}">

                    <td>${req.id}</td>
                    <td>${req.tokenId}</td>
                    <td>${req.needyName}</td>
                    <td>${req.mobile}</td>
                    <td>${req.address}</td>
                    <td>${req.pinCode}</td>
                    <td>${req.state}</td>
                    <td>${req.problem}</td>
                    <td>${req.familyOccupation}</td>
                    <td>${req.financialCondition}</td>
                    <td>${req.comment}</td>

                    <!-- FILE VIEW -->
<td>
    ${
        fileLink
        ? `<a href="${fileLink}" target="_blank">View</a>`
        : "No File"
    }
</td>

                    <!-- STATUS -->
                    <td>

                        <select class="status-dropdown ${currentStatus.toLowerCase()}"
                                data-current="${currentStatus}">

                            <option value="PENDING"
                                ${currentStatus === 'PENDING' ? 'selected' : ''}>
                                PENDING
                            </option>

                            <option value="WORKING"
                                ${currentStatus === 'WORKING' ? 'selected' : ''}>
                                WORKING
                            </option>

                            <option value="ACCEPTED"
                                ${currentStatus === 'ACCEPTED' ? 'selected' : ''}>
                                ACCEPTED
                            </option>

                            <option value="REJECTED"
                                ${currentStatus === 'REJECTED' ? 'selected' : ''}>
                                REJECTED
                            </option>

                        </select>

                        <input type="number"
                               class="amount-input"
                               id="amt-${req.id}"
                               value="${amountValue}"
                               placeholder="₹">

                        <button class="save-btn"
                                onclick="updateStatus(${req.id})">
                            Save
                        </button>

                    </td>

                </tr>
                `;

                table.innerHTML += row;
            });
        });
}


// ================= CHANGE COLOR =================
function changeStatusColor(select) {
    select.className =
        "status-dropdown " + select.value.toLowerCase();
}


// ================= UPDATE STATUS =================
function updateStatus(id) {

    let row = document.getElementById("row-" + id);

    let dropdown =
        row.querySelector(".status-dropdown");

    let amountInput =
        row.querySelector(".amount-input");

    let newStatus = dropdown.value;

    let amount = amountInput.value || "";

    let currentStatus =
        dropdown.getAttribute("data-current") || "";

    // 🔴 LOCK AFTER ACCEPT / REJECT
    if (currentStatus === "ACCEPTED"
        || currentStatus === "REJECTED") {

        showPopup(
            "After accepted/rejected you cannot change status"
        );

        dropdown.value = currentStatus;

        changeStatusColor(dropdown);

        return;
    }

    // 🔴 ACCEPT NEEDS AMOUNT
    if (newStatus === "ACCEPTED") {

        if (amount === ""
            || parseFloat(amount) < 1) {

            showPopup(
                "Please enter amount greater than 0 before accepting"
            );

            dropdown.value = currentStatus;

            changeStatusColor(dropdown);

            return;
        }
    }

    // ✅ CLEAR AMOUNT
    if (newStatus === "PENDING"
        || newStatus === "WORKING") {

        amount = "";

        amountInput.value = "";
    }

    // 🔴 REJECT AUTO 0
    if (newStatus === "REJECTED") {

        amount = "0";

        amountInput.value = amount;
    }

    // ================= API =================
    fetch(UPDATE_BENEFICIARY_STATUS + id, {

        method: "PUT",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            status: newStatus,
            amount: amount
        })

    })

    .then(res => res.text())

    .then(msg => {

        dropdown.setAttribute(
            "data-current",
            newStatus
        );

        changeStatusColor(dropdown);

        if (newStatus === "PENDING") {

            showPopup("Status changed to Pending");

        } else if (newStatus === "WORKING") {

            showPopup("Status changed to Working");

        } else if (newStatus === "ACCEPTED") {

            showPopup(
                "Status changed to Accepted with amount " + amount
            );

        } else if (newStatus === "REJECTED") {

            showPopup(
                "Status changed to Rejected with amount 0.00"
            );

        } else {

            showPopup(msg);
        }

        loadRequests();

    })

    .catch(err => {

        console.error(err);

        showPopup("Update failed");

        dropdown.value = currentStatus;

        changeStatusColor(dropdown);
    });
}


// ================= INIT =================
window.onload = function () {
    loadRequests();
};