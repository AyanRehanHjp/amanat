loadPostHolders();

/* LOAD DATA */

function loadPostHolders(){

fetch("/postholder/getAllPostHolders")

.then(res=>res.json())

.then(data=>{

let tbody=document.querySelector("#postholderTable tbody");

tbody.innerHTML="";

data.forEach(ph=>{

let row=`
<tr>

<td>${ph.id}</td>
<td>${ph.name}</td>
<td>${ph.post}</td>
<td>${ph.contactNo}</td>
<td>${ph.address}</td>

</tr>
`;

tbody.innerHTML+=row;

});

});

}


/* ADD POST HOLDER */

document.getElementById("postholderForm").addEventListener("submit",function(e){

e.preventDefault();

let data={

name:document.getElementById("name").value,
post:document.getElementById("post").value,
contactNo:document.getElementById("contactNo").value,
address:document.getElementById("address").value

};

fetch("/postholder/addPostHolder",{

method:"POST",

headers:{
"Content-Type":"application/json"
},

body:JSON.stringify(data)

})

.then(res=>res.text())

.then(msg=>{

alert(msg);

loadPostHolders();

document.getElementById("postholderForm").reset();

});

});

let row = `
<tr>

<td>${ph.id}</td>
<td>${ph.name}</td>
<td>${ph.post}</td>
<td>${ph.contactNo}</td>
<td>${ph.address}</td>

<td>
<button onclick="deletePostHolder(${ph.id})" class="deleteBtn">
Delete
</button>
</td>

</tr>
`;
function deletePostHolder(id){

if(!confirm("Are you sure you want to delete this post holder?")){
return;
}

fetch("/postholder/deletePostHolder/"+id,{
method:"DELETE"
})

.then(res=>res.text())

.then(msg=>{
alert(msg);
loadPostHolders();
});

}