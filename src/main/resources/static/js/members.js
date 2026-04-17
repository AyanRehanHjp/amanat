// ================= LOAD MEMBERS =================
function loadMembers(){
fetch("http://localhost:9000/members/allmembers")
.then(response => response.json())
.then(data => {

let table = document.querySelector("#membersTable tbody");
table.innerHTML = "";

data.forEach(member => {

let row = `
<tr>
<td>${member.id}</td>
<td>${member.memberId ?? ""}</td>
<td>${member.prefix ?? ""}</td>
<td style="text-align:left">${member.firstname ?? ""} ${member.lastname ?? ""}</td>
<td>${member.joiningYear ?? ""}</td>
<td>${member.mobile ?? ""}</td>
<td>${member.address ?? ""}</td>
<td class="${member.status === 'Active' ? 'status-active' : 'status-inactive'}">
${member.status}
</td>
<td>${member.joinedBy ?? ""}</td>
</tr>
`;

table.innerHTML += row;

});

});
}


// ================= YEAR DROPDOWN =================
function loadYears(){
let yearDropdown = document.getElementById("joiningYear");

for(let year = 2021; year <= 2099; year++){
    let option = document.createElement("option");
    option.value = year;
    option.text = year;
    yearDropdown.appendChild(option);
}
}


// ================= ADD MEMBER =================
function addMember(){

let fullMobile = document.getElementById("countryCode").value +
                 document.getElementById("mobile").value;

let member = {
    prefix: document.getElementById("prefix").value,
    firstname: document.getElementById("firstname").value,
    lastname: document.getElementById("lastname").value,
    joiningYear: document.getElementById("joiningYear").value,
    mobile: fullMobile,
    address: document.getElementById("address").value,
    status: document.getElementById("status").value,
    joinedBy: document.getElementById("joinedBy").value
};

fetch("http://localhost:9000/members/addMember", {
    method: "POST",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify(member)
})
.then(res => {
    if(res.ok){
        alert("Member Added Successfully");
        loadMembers();
    } else {
        alert("Error adding member");
    }
})
.catch(err => {
    console.error(err);
    alert("Server error");
});

}


// ================= BACK BUTTON =================
function goBack(){
    window.location.href="/admin.html";
}


// ================= INIT =================
window.onload = function(){
    loadMembers();
    loadYears();
};