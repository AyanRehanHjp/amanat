// 🔙 Back button
function goHome(){
    window.location.href = "/admin.html";
}

// 🔥 Year dropdown setup (safe way)
window.onload = function () {

    const reportYear = document.getElementById("reportYear");

    for(let y = 2021; y <= 2099; y++){
        let option = document.createElement("option");
        option.value = y;
        option.text = y;
        reportYear.appendChild(option);
    }

    reportYear.value = new Date().getFullYear();
};


// 🔥 Monthly Report API call
function loadReport(){

    let year = document.getElementById("reportYear").value;

    fetch(`/incomeDet/monthly-report?year=${year}`)
    .then(res => res.json())
    .then(data => {

        let tbody = document.querySelector("#reportTable tbody");
        tbody.innerHTML = "";

        data.forEach(row => {

            let tr = document.createElement("tr");

            tr.innerHTML = `
                <td>${row[0]}</td>
                ${row.slice(1).map(val =>
                    `<td style="color:${val == 0 ? 'red' : 'green'}">${val}</td>`
                ).join("")}
            `;

            tbody.appendChild(tr);
        });

    })
    .catch(error => {
        console.error("Error loading report:", error);
    });
}


// 🖨️ Print PDF
function printPDF() {
    window.print();
}