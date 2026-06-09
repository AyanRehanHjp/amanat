let editingMemberId = null;
let currentPage = 0;
let pageSize = 30;
let totalPages = 1;

// ================= LOAD MEMBERS =================
function loadMembers(){
showLoader();
fetch(`${ALL_MEMBERS}?page=${currentPage}&size=${pageSize}`,{
    headers:{
        "Authorization":"Bearer " + token
    }
}).then(res => res.json())
.then(data => {

let table = document.querySelector("#membersTable tbody");
table.innerHTML = "";

//data.forEach(member => {
totalPages = data.totalPages;
data.content.forEach(member => {
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
<td>${member.joinedBy ?? ""}</td>
<td>${member.approvalFlag ?? "P"}</td>
<td><button onclick="editMember('${member.memberId}')">Edit</button></td>

</tr>
`;

table.innerHTML += row;

});

document.getElementById("pageInfo")
.innerText =
`Page ${currentPage + 1} of ${totalPages}`;
hideLoader();
})
.catch(err => {
hideLoader();
console.error("Error loading members:", err);

});
}
//Search method
function renderSearchResult(data){

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
            <td>${member.joinedBy ?? ""}</td>
            <td>${member.approvalFlag ?? "P"}</td>
            <td>
                <button onclick="editMember('${member.memberId}')">
                    Edit
                </button>
            </td>
        </tr>
        `;

        table.innerHTML += row;
    });

    document.getElementById("pageInfo").innerText =
        `Search Result : ${data.length}`;
}
// ================= EDIT MEMBER =================
function editMember(memberId){
    showLoader();

fetch(`${ALL_MEMBERS}?page=${currentPage}&size=${pageSize}`,{
    headers:{
        "Authorization":"Bearer " + token
    }
}).then(res => res.json())
.then(data => {

let m = data.content.find(x => x.memberId === memberId);

if(!m){
    showPopup("Member not found");
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
document.getElementById("approvalFlag").value = m.approvalFlag || "P";
// heading
document.getElementById("formTitle").innerText = "Update Member";

// scroll
window.scrollTo({ top: 0, behavior: "smooth" });
document.querySelector(".add-member-box").classList.remove("add-mode");
document.querySelector(".add-member-box").classList.add("edit-mode");
hideLoader();
})

.catch(err => {
hideLoader();
console.error("Error fetching member:", err);
});
}


// ================= SAVE (ADD + UPDATE) =================
function saveMember(){
    showLoader();
let fullMobile = document.getElementById("countryCode").value +
                 document.getElementById("mobile").value;
let mobile =
document.getElementById("mobile").value;

if(mobile.length !== 10){
    hideLoader();
    showPopup("Mobile Number must be 10 digits");
    return;
}
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
    joinedBy: document.getElementById("joinedBy").value,
    approvalFlag:document.getElementById("approvalFlag").value
};

// 🔥 decide API
let url = editingMemberId
    ? UPDATE_MEMBER + editingMemberId
    : ADD_MEMBER;

let method = editingMemberId ? "PUT" : "POST";

fetch(url,{
    method: method,
headers: {
    "Content-Type":"application/json",
    "Authorization":"Bearer " + token
},
    body: JSON.stringify(member)
})
.then(async res => {

    let msg = await res.text();

    if(!res.ok){

        throw new Error(msg );
    }

    return msg;
})
.then(msg => {
    hideLoader();

    showPopup(msg);

    loadMembers();
    resetForm();

})
.catch(err => {
    hideLoader();

    console.error("Save error:", err);

    showPopup(
        err.message || "Something went wrong"
    );
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
document.getElementById("approvalFlag").value ="P";

document.querySelector(".add-member-box").classList.remove("edit-mode");
document.querySelector(".add-member-box").classList.add("add-mode");}


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
loadYears();
document.querySelector(".add-member-box").classList.add("add-mode");
};
// ================= For Next and Previous page data =================

function nextPage(){

    if(currentPage < totalPages - 1){

        currentPage++;

        loadMembers();
    }
}

function previousPage(){

    if(currentPage > 0){

        currentPage--;

        loadMembers();
    }
}

//=========SEARCH MEMBERS=====================
function searchMembers(){

    showLoader();

    let name = document.getElementById("searchName").value.trim();
    let mobile = document.getElementById("searchMobile").value.trim();

    if(!name && !mobile){

        hideLoader();

        showPopup("Enter Name or Mobile Number");
        return;
    }

    // Name Search
    if(name && !mobile){

        fetch(`/members/searchByName?name=${encodeURIComponent(name)}`,
          {
              headers: { "Authorization": "Bearer " + token
              }
          })
        .then(async res => {

            if(!res.ok){
                throw new Error(await res.text());
            }

            return res.json();
        })
        .then(data => {

            renderSearchResult(data);

            hideLoader();
        })
        .catch(err => {

            hideLoader();

            showPopup(err.message);
        });

        return;
    }

    // Mobile Search
    if(!name && mobile){

        fetch(`/members/searchByMobile?mobile=${encodeURIComponent(mobile)}`,
        {
            headers: { "Authorization": "Bearer " + token
            }
        })
        .then(async res => {

            if(!res.ok){
                throw new Error(await res.text());
            }

            return res.json();
        })
        .then(data => {

            renderSearchResult(data);

            hideLoader();
        })
        .catch(err => {

            hideLoader();

            showPopup(err.message);
        });

        return;
    }

    // Name + Mobile
fetch(`/members/searchByName?name=${encodeURIComponent(name)}`,{
    headers:{
        "Authorization":"Bearer " + token
    }
})    .then(async res => {

        if(!res.ok){
            throw new Error(await res.text());
        }

        return res.json();
    })
    .then(data => {

        let filtered = data.filter(m =>
            m.mobile &&
            m.mobile.includes(mobile)
        );

        hideLoader();

        if(filtered.length === 0){

            showPopup(
                "This mobile number is not linked with this name. Please search separately."
            );
            return;
        }

        renderSearchResult(filtered);

    })
    .catch(err => {

        hideLoader();

        showPopup(err.message);
    });
}
//=========RESET MEMBERS=====================

function resetSearch(){

    document.getElementById("searchName").value = "";
    document.getElementById("searchMobile").value = "";

    document.querySelector("#membersTable tbody").innerHTML = "";

    document.getElementById("pageInfo").innerText = "";
}