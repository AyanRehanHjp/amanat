// 🔙 Back button (Go to dashboard)
function goHome(){

  let role = localStorage.getItem("role");

  if(role && role.toUpperCase() === "ADMIN"){
      window.location.href = "/admin.html";
  }
  else if(role && role.toUpperCase() === "USER"){
      window.location.href = "/userdetails.html";
  }
  else {
      window.location.href = "/login.html";
  }
}

// 🔥 Page Load (Dropdown Setup)
  window.onload = function () {

      const reportYear = document.getElementById("reportYear");
      if(!reportYear) return;

      for(let y = 2021; y <= 2099; y++){
          let option = document.createElement("option");
          option.value = y;
          option.text = y;
          reportYear.appendChild(option);
      }

      reportYear.value = new Date().getFullYear();

      // ✅ Pay button only for user
      let role = localStorage.getItem("role");

      if(role && role.toUpperCase() === "ADMIN"){
          let payBtn = document.querySelector(".pay-btn");
          if(payBtn){
              payBtn.style.display = "none";
          }
      }
  };

// 🔥 Load Monthly Report API
function loadReport(){

    let year = document.getElementById("reportYear").value;

    let params = new URLSearchParams(window.location.search);
    let type = params.get("type");

    let apiUrl;
 // Api url for user and admin
let role = localStorage.getItem("role");

if(role && role.toUpperCase() === "USER"){
    apiUrl = `/incomeDet/my-monthly-report?year=${year}`;
}
else if(role && role.toUpperCase() === "ADMIN"){
    apiUrl = `/incomeDet/monthly-report?year=${year}`;
}
else {
    alert("Unauthorized access");
    window.location.href = "/login.html";
    return;
}

    // 🔥 GET TOKEN FROM LOCAL STORAGE
    let token = localStorage.getItem("token");

    console.log("TOKEN:", token); // debug check

    fetch(apiUrl, {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token   // 🔥 IMPORTANT FIX
        }
    })
    .then(res => {
        if(!res.ok){
            throw new Error("API response not OK");
        }
        return res.json();
    })
    .then(data => {

        let tbody = document.querySelector("#reportTable tbody");
        if(!tbody) return;

        tbody.innerHTML = "";

        if(!data || data.length === 0){
            tbody.innerHTML = `
                <tr>
                    <td colspan="16" style="color:gray;">
                        No data available
                    </td>
                </tr>
            `;
            return;
        }

        let totals = new Array(12).fill(0);

        data.forEach(row => {

            let tr = document.createElement("tr");

            let html = `
                <td>${row[0]}</td>
                <td>${row[1]}</td>
                <td>${row[2]}</td>
                <td>${row[3]}</td>
            `;

            for(let i = 4; i <= 15; i++){
                let val = Number(row[i] || 0);

                totals[i - 4] += val;

                html += `
                    <td data-value="${val}"
                        style="color:${val == 0 ? 'red' : 'green'}">
                        ${val}
                    </td>
                `;
            }

            tr.innerHTML = html;
            tbody.appendChild(tr);
        });

        let totalRow = document.createElement("tr");

     let totalHTML = `
         <td style="font-weight:bold; background:#e8f5e9;"></td>

         <td style="font-weight:bold; background:#e8f5e9;"></td>

         <td style="font-weight:bold; background:#e8f5e9;"></td>

         <td style="font-weight:bold; background:#e8f5e9;">
         TOTAL
         </td>
     `;

        totals.forEach(val => {
            totalHTML += `
                <td style="font-weight:bold; color:blue;">
                    ${val}
                </td>
            `;
        });

        totalRow.innerHTML = totalHTML;
        tbody.appendChild(totalRow);

    })
    .catch(error => {
        console.error("Error loading report:", error);
        alert("Something went wrong while loading report!");
    });
}


// =======🖨️ Print PDF========================
function printPDF(){

    // First show all columns
    for(let i = 4; i <= 15; i++){
        toggleColumn(i, true);
    }

    let checked = document.querySelectorAll("#monthFilter input:checked");

    // If some months selected then hide others
    if(checked.length > 0){

        // Hide all month columns first
        for(let i = 4; i <= 15; i++){
            toggleColumn(i, false);
        }

        // Show only selected months
        checked.forEach(cb => {
            toggleColumn(parseInt(cb.value), true);
        });
    }

    // Add print mode class
    document.body.classList.add("print-mode");

    // Small delay so browser recalculates layout properly
    setTimeout(() => {

        window.print();

        // Restore everything after print
        document.body.classList.remove("print-mode");

        for(let i = 4; i <= 15; i++){
            toggleColumn(i, true);
        }

    }, 300);
}



// ============👀 Show / Hide Column===================
function toggleColumn(index, show){

    document.querySelectorAll("#reportTable tr").forEach(row => {

        if(row.children[index]){

            if(show){
                row.children[index].classList.remove("hide-column");
            }
            else{
                row.children[index].classList.add("hide-column");
            }
        }
    });
}

function goToPayment(){
    window.location.href = "/scan&pay.html";
}