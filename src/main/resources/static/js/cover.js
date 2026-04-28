function loadTrustData(){

    fetch("/report/total-expenditure")
    .then(res => res.text())
    .then(data => {

        document.getElementById("totalExpense").innerText = "₹" + data;

    })
    .catch(err => {
        console.log("Error fetching total expenditure:", err);
    });

}

loadTrustData();