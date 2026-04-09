function loadIncome() {

    fetch("http://localhost:9000/incomeDet/showIncomeDet")
        .then(response => response.json())
        .then(data => {

            let tableBody = document.querySelector("#incomeTable tbody");
            tableBody.innerHTML = "";

            data.forEach(item => {
                let row = `
                    <tr>
                        <td>${item.id}</td>
                        <td>${item.memberId}</td>
                        <td>${item.amount}</td>
                        <td>${item.month}</td>
                        <td>${item.year}</td>
                    </tr>
                `;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => {
            console.error("Error:", error);
        });
}
function goHome(){
    window.location.href="/admin.html";
}

// 🔥 year dropdown
const reportYear = document.getElementById("reportYear");

for(let y = 2021; y <= 2099; y++){
    let option = document.createElement("option");
    option.value = y;
    option.text = y;
    reportYear.appendChild(option);
}

reportYear.value = new Date().getFullYear();


// 🔥 monthly report
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

    });
}