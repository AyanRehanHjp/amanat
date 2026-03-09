fetch("http://localhost:9000/members/allmembers")

.then(response => response.json())

.then(data => {

let table = document.querySelector("#membersTable tbody");

data.forEach(member => {

let row = `
<tr>
<td>${member.id}</td>
<td>${member.memberId}</td>
<td style="text-align:right">${member.prefix}</td>
<td style="text-align:left">${member.firstname ?? ""}</td>
<td>${member.lastname ?? ""}</td>
<td>${member.joiningYear}</td>
<td>${member.mobile}</td>
<td>${member.address}</td>
<td class="${member.status === 'Active' ? 'status-active' : 'status-inactive'}">
${member.status}
</td>
<td>${member.joinedBy}</td>
</tr>
`;

table.innerHTML += row;

});

});
function goBack(){
window.location.href="/admin.html";
}