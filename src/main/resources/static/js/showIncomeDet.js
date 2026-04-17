// ===============================
// 🔙 Back button (Go to dashboard)
// ===============================
function goHome(){
    window.location.href = "/admin.html";
}

// ===============================
// 🔥 Page Load (Dropdown Setup)
// ===============================
window.onload = function () {

    const reportYear = document.getElementById("reportYear");

    // Safety check (element mila ya nahi)
    if(!reportYear) return;

    // Year dropdown populate (2021 → 2099)
    for(let y = 2021; y <= 2099; y++){
        let option = document.createElement("option");
        option.value = y;
        option.text = y;
        reportYear.appendChild(option);
    }

    // Default current year select
    reportYear.value = new Date().getFullYear();
};

// ===============================
// 🔥 Load Monthly Report API
// ===============================
function loadReport(){

    let year = document.getElementById("reportYear").value;

    fetch(`/incomeDet/monthly-report?year=${year}`)
    .then(res => {

        // 🔥 response check (important)
        if(!res.ok){
            throw new Error("API response not OK");
        }

        return res.json();
    })
    .then(data => {

        let tbody = document.querySelector("#reportTable tbody");

        // Safety check
        if(!tbody) return;

        // Clear previous data
        tbody.innerHTML = "";

        // If no data
        if(!data || data.length === 0){
            tbody.innerHTML = `
                <tr>
                    <td colspan="13" style="color:gray;">
                        No data available
                    </td>
                </tr>
            `;
            return;
        }

        // Loop rows
        data.forEach(row => {

            let tr = document.createElement("tr");

            // 🔥 IMPORTANT: data-value added for CSS coloring
            tr.innerHTML = `
                <td>${row[0]}</td>
                ${row.slice(1).map(val =>
                    `<td data-value="${val}"
                         style="color:${val == 0 ? 'red' : 'green'}">
                         ${val}
                     </td>`
                ).join("")}
            `;

            tbody.appendChild(tr);
        });

    })
    .catch(error => {
        console.error("Error loading report:", error);
        alert("Something went wrong while loading report!");
    });
}

// ===============================
// 🖨️ Print PDF
// ===============================
function printPDF() {
    window.print(); // browser print
}