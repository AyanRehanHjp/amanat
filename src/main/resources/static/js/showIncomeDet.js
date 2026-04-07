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