function loadTrustData(){

fetch("/api/trustOverview")

.then(res => res.json())

.then(data => {

document.getElementById("totalIncome").innerText="₹"+data.totalIncome

document.getElementById("totalExpense").innerText="₹"+data.totalExpense

document.getElementById("balance").innerText="₹"+data.balance

})

}

loadTrustData()