// LOAD ALL EXPENDITURE DATA
function loadData(){
    fetch(ALL_EXPENDITURE)
    .then(response => response.json())
    .then(data => {

        let table = document.querySelector("#expTable tbody");
        table.innerHTML = "";

        data.forEach((exp, index) => {

            let date = exp.expDate
                ? new Date(exp.expDate).toLocaleDateString()
                : "";

            let fileLink = exp.supDoc
                ? `<a href="${exp.supDoc}" target="_blank">View</a>`
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
                        ? `<button disabled>Generated</button>`
                        : `<button onclick="generateReceipt(${exp.id})">Generate</button>`
                    }
                </td>

                <td>
                <a href="${exp.receiptImageUrl}" target="_blank">
                        View
                    </a>
                </td>
            </tr>
            `;

            table.innerHTML += row;
        });

        // Add total row
        addTotalRow();

        // Show next receipt number
        let rows = document.querySelectorAll("#expTable tbody tr");

        let lastReceipt = rows.length > 1
            ? rows[rows.length - 2].children[1].innerText
            : "000";

        document.getElementById("receiptNo").value =
            String(parseInt(lastReceipt) + 1).padStart(3,'0');

    })
    .catch(err => {
        showPopup("Error loading data: " + err.message);
    });
}

// Page load
window.onload = function(){
    document.getElementById("year").value = new Date().getFullYear();
    loadData();
};

// Back button
function goBack(){
    window.location.href = "/admin.html";
}

// Save expenditure
function saveExp(){

    let formData = new FormData();

    let data = {
        name: document.getElementById("name").value,
        address: document.getElementById("address").value,
        amount: document.getElementById("amount").value,
        expDate: document.getElementById("expDate").value,
        year: document.getElementById("year").value,
        problem: document.getElementById("problem").value
    };

    formData.append("expenditure", new Blob([JSON.stringify(data)], {
        type: "application/json"
    }));

    let file = document.getElementById("supDoc").files[0];
    if(file){

        let allowedTypes = [
            "image/jpeg",
            "image/jpg",
            "image/png"
        ];

        if(!allowedTypes.includes(file.type)){

            showPopup("PDF not allowed. Only JPG, JPEG, PNG allowed.");

            return;
        }
    }

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
//        location.reload();
    })
    .catch(err => {
        showPopup("Error: " + err.message);
    });
}

// Add total amount row
function addTotalRow(){

    let rows = document.querySelectorAll("#expTable tbody tr");

    let total = 0;

    rows.forEach(row => {

        let amountText = row.querySelector(".amount")
            .innerText.replace("₹", "").trim();

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

// Redirect to receipt page
function generateReceipt(id){
    window.location.href = "/recpdfgen.html?id="+id;
}