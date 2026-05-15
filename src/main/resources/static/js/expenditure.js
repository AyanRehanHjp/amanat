// LOAD DATA FUNCTION
function loadData(){

    fetch(ALL_EXPENDITURE)

    .then(response => response.json())

    .then(data => {

        let table = document.querySelector("#expTable tbody");

        table.innerHTML = "";

        data.forEach((exp, index) => {

            let date = exp.expDate ? new Date(exp.expDate).toLocaleDateString() : "";

            let fileLink = exp.supDoc
            ? `<a href="${EXPENDITURE_FILE_VIEW}${exp.supDoc}" target="_blank">View</a>`
            : "No File";

            let row = `
            <tr>
                <td>${index + 1}</td>
                <td>${exp.receiptNo}</td>
                <td>${exp.name}</td>
                <td>${exp.address}</td>
                <td class="amount">₹ ${exp.amount}</td>
                <td>${date}</td>
                <td>${exp.year}</td>
                <td>${exp.problem}</td>
                <td>${fileLink}</td>

                <td>
                    ${
                        exp.receiptGenerated === "Y"

                        ?

                        `<button disabled>Generated</button>`

                        :

                        `<button onclick="generateReceipt(${exp.id})">Generate</button>`
                    }
                </td>

                <td>
                    <a href="/receipts/receipt_${exp.id}.pdf" target="_blank">
                        View
                    </a>
                </td>

            </tr>
            `;

            table.innerHTML += row;
        });

        addTotalRow();
    })

    .catch(err => {

        showPopup("Error loading data: " + err.message);
    });
}

// PAGE LOAD
window.onload = function(){

    document.getElementById("year").value = new Date().getFullYear();

    loadData();
};

// BACK BUTTON
function goBack(){

    window.location.href = "/admin.html";
}

// SAVE FUNCTION
function saveExp(){

    let formData = new FormData();

    let data = {
        name: document.getElementById("name").value,
        address: document.getElementById("address").value,
        amount: document.getElementById("amount").value,
        expDate: document.getElementById("expDate").value,
        year: document.getElementById("year").value,
        receiptNo: document.getElementById("receiptNo").value,
        problem: document.getElementById("problem").value
    };

    formData.append("expenditure", new Blob([JSON.stringify(data)], {
        type: "application/json"
    }));

    let file = document.getElementById("supDoc").files[0];

    if(file){

        formData.append("file", file);
    }

    fetch(ADD_EXPENDITURE, {
        method: "POST",
        body: formData
    })

    .then(res => res.text())

    .then(msg => {

        showPopup(msg);

        loadData();
    })

    .catch(err => {

        showPopup("Error: " + err.message);
    });
}

// TOTAL ROW
function addTotalRow(){

    let rows = document.querySelectorAll("#expTable tbody tr");

    let total = 0;

    rows.forEach(row => {

        let amountText = row.querySelector(".amount").innerText.replace("₹", "").trim();

        total += parseFloat(amountText) || 0;
    });

    let tbody = document.querySelector("#expTable tbody");

    let tr = document.createElement("tr");

    tr.classList.add("total-row");

    tr.innerHTML = `
        <td colspan="4"></td>
        <td><b>Total</b></td>
        <td><b>₹ ${total.toLocaleString('en-IN')}</b></td>
        <td colspan="5"></td>
    `;

    tbody.appendChild(tr);
}

// GENERATE RECEIPT
function generateReceipt(id){

    window.location.href = "/recpdfgen.html";
}