function loadTrustData(){

    let income = 0;
    let expense = 0;

    // 🔴 Expense
    fetch("/report/total-expenditure")
    .then(res => res.text())
    .then(data => {
        expense = Number(data);
        document.getElementById("totalExpense").innerText = "₹" + expense;
        updateBalance();
    });

    // 🟢 Income
    fetch("/report/total-income")
    .then(res => res.text())
    .then(data => {
        income = Number(data);
        document.getElementById("totalIncome").innerText = "₹" + income;
        updateBalance();
    });

    function updateBalance(){

        let left = income - expense;

        // Center Left Amount
        document.getElementById("balance").innerText = "₹" + left;

        // Secretary amount
        let sec = Number(document.getElementById("secretaryAmount").innerText);

        // Main Account
        document.getElementById("mainBalance").innerText = "₹" + (left - sec);
    }
}

loadTrustData();


// ================= YEARLY DATA =================

function loadYearlyData(){

    fetch("/report/yearly")
    .then(res => res.json())
    .then(data => {

        data.forEach(item => {

            let year = item.year;

            // 🟢 Income
            let incomeEl = document.getElementById("income-" + year);
            if(incomeEl){
                incomeEl.innerText = item.income;
            }

            // 🔴 Expense
            let expenseEl = document.getElementById("expense-" + year);
            if(expenseEl){
                expenseEl.innerText = item.expense;
            }

            // 🔥 Left
            let leftEl = document.getElementById("left-" + year);
            if(leftEl){
                leftEl.innerText = item.left;
            }

        });

    })
    .catch(err => {
        console.log("Yearly Data Error:", err);
    });
}

loadYearlyData();