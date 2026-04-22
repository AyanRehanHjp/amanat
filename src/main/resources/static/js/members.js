let editingMemberId = null;


// ================= LOAD MEMBERS =================
function loadMembers(){
fetch("http://localhost:9000/members/allmembers")
.then(res => res.json())
.then(data => {

let table = document.querySelector("#membersTable tbody");
table.innerHTML = "";

data.forEach(member => {

let row = `
<tr>
<td>${member.id}</td>
<td>${member.memberId}</td>
<td>${member.prefix ?? ""}</td>
<td>${(member.firstname ?? "") + " " + (member.lastname ?? "")}</td>
<td>${member.joiningYear ?? ""}</td>
<td>${member.mobile ?? ""}</td>
<td>${member.address ?? ""}</td>
<td>${member.status ?? ""}</td>

<td>
<button onclick="editMember('${member.memberId}')">Edit</button>
</td>

<td>${member.joinedBy ?? ""}</td>
</tr>
`;

table.innerHTML += row;

});

})
.catch(err => {
console.error("Error loading members:", err);
});
}


// ================= EDIT MEMBER =================
function editMember(memberId){

fetch("http://localhost:9000/members/allmembers")
.then(res => res.json())
.then(data => {

let m = data.find(x => x.memberId === memberId);

if(!m){
    alert("Member not found");
    return;
}

editingMemberId = m.memberId;

// ✅ fill fields
document.getElementById("memberId").value = m.memberId || "";
document.getElementById("memberId").disabled = true;   // 🔥 lock ID in update

document.getElementById("prefix").value = m.prefix || "";
document.getElementById("firstname").value = m.firstname || "";
document.getElementById("lastname").value = m.lastname || "";
document.getElementById("joiningYear").value = m.joiningYear || "";

document.getElementById("mobile").value = m.mobile ? m.mobile.replace("+91","") : "";
document.getElementById("address").value = m.address || "";
document.getElementById("status").value = m.status || "Active";
document.getElementById("joinedBy").value = m.joinedBy || "";

// heading
document.getElementById("formTitle").innerText = "Update Member";

// scroll
window.scrollTo({ top: 0, behavior: "smooth" });

})
.catch(err => {
console.error("Error fetching member:", err);
});
}


// ================= SAVE (ADD + UPDATE) =================
function saveMember(){

let fullMobile = document.getElementById("countryCode").value +
                 document.getElementById("mobile").value;

// 🔥 FIXED memberId logic
let member = {
    memberId: editingMemberId ? editingMemberId : document.getElementById("memberId").value,
    prefix: document.getElementById("prefix").value,
    firstname: document.getElementById("firstname").value,
    lastname: document.getElementById("lastname").value,
    joiningYear: document.getElementById("joiningYear").value,
    mobile: fullMobile,
    address: document.getElementById("address").value,
    status: document.getElementById("status").value,
    joinedBy: document.getElementById("joinedBy").value
};

// 🔥 decide API
let url = editingMemberId
    ? "http://localhost:9000/members/updateMember/" + editingMemberId
    : "http://localhost:9000/members/addMember";

let method = editingMemberId ? "PUT" : "POST";

fetch(url,{
    method: method,
    headers: {
        "Content-Type":"application/json"
    },
    body: JSON.stringify(member)
})
.then(res => res.text())
.then(msg => {

    alert(msg);
    loadMembers();
    resetForm();

})
.catch(err => {
    console.error("Save error:", err);
    alert("Something went wrong");
});
}


// ================= RESET FORM =================
function resetForm(){

editingMemberId = null;

document.getElementById("formTitle").innerText = "Add New Member";

// 🔥 enable ID again
document.getElementById("memberId").disabled = false;

// clear fields
document.getElementById("memberId").value = "";
document.getElementById("prefix").value = "";
document.getElementById("firstname").value = "";
document.getElementById("lastname").value = "";
document.getElementById("joiningYear").value = "";
document.getElementById("mobile").value = "";
document.getElementById("address").value = "";
document.getElementById("joinedBy").value = "";
document.getElementById("status").value = "Active";
}


// ================= YEAR =================
function loadYears(){
let yearDropdown = document.getElementById("joiningYear");

for(let y=2021; y<=2099; y++){
    let opt = document.createElement("option");
    opt.value = y;
    opt.text = y;
    yearDropdown.appendChild(opt);
}
}


// ================= BACK =================
function goBack(){
window.location.href="/admin.html";
}


// ================= INIT =================
window.onload = function(){
loadMembers();
loadYears();
};