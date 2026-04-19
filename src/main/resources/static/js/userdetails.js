/* IMAGE PREVIEW */
function previewImage(event){
    const reader = new FileReader();
    reader.onload = function(){
        document.getElementById("profilePreview").src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]);
}

/* TOKEN CHECK */
const token = localStorage.getItem("token");
if(!token){
    window.location.href="/login.html";
}

/* FETCH USER */
function loadUser(){

const userId = localStorage.getItem("userId");

fetch("http://localhost:9000/signUp/retriveUser/" + userId,{
    headers:{
        "Authorization":"Bearer "+token
    }
})
.then(res=>{
    if(res.status===403){
        window.location.href="/login.html";
    }
    return res.json();
})
.then(data=>{


    localStorage.setItem("userId",data.id);

    firstName.value=data.firstName||"";
    lastName.value=data.lastName||"";
    mobile.value=data.mobile||"";
    email.value=data.email||"";
    userName.value=data.userName||"";
    role.value=data.role||"";
memberId.value = data.memberId || (data.member && data.member.memberId) || "";

    /* ⭐ PROFILE IMAGE LOAD WITH AUTH HEADER + SPACE FIX */
    if(data.profilePicture){

        fetch(
            "http://localhost:9000/uploads/profile/" +
            encodeURIComponent(data.profilePicture),
        {
            headers:{
                "Authorization":"Bearer "+token
            }
        })
        .then(res=>res.blob())
        .then(blob=>{
            const url = URL.createObjectURL(blob);
            document.getElementById("profilePreview").src = url;
        })
        .catch(()=>{
            document.getElementById("profilePreview").src="/images/defaultpic.png";
        });

    }else{
        document.getElementById("profilePreview").src="/images/defaultpic.png";
    }

    dateOfJoining.value=data.dateOfJoining||"";
    gender.value=data.gender||"";
    city.value=data.city||"";
    state.value=data.state||"";
    country.value=data.country||"";
    pinCode.value=data.pinCode||"";
    joinedBy.value=data.joinedBy||"";

});
}

/* PAGE LOAD */
loadUser();


/* UPDATE USER */
function updateUser(){

const userId=localStorage.getItem("userId");

const formData=new FormData();

formData.append("dateOfJoining",dateOfJoining.value);
formData.append("gender",gender.value);
formData.append("city",city.value);
formData.append("state",state.value);
formData.append("country",country.value);
formData.append("pinCode",pinCode.value);
formData.append("joinedBy",joinedBy.value);

const file=document.getElementById("profileImage").files[0];

if(file){
    formData.append("profileImage",file);
}

fetch("http://localhost:9000/signUp/updateUser/"+userId,{
    method:"PUT",
    headers:{
        "Authorization":"Bearer "+token
    },
    body:formData
})
.then(res=>res.text())
.then(msg=>{
    alert(msg);

    /* ⭐ reload ki jagah direct latest image load */
    loadUser();
});

}


/* LOGOUT */
function logout(){
localStorage.removeItem("token");
localStorage.removeItem("userId");
localStorage.removeItem("userName");
window.location.href="/login.html";
}
function deleteProfilePic(){

const userId = localStorage.getItem("userId");

fetch("http://localhost:9000/signUp/removeProfilePic/"+userId,{
    method:"DELETE",
    headers:{
        "Authorization":"Bearer "+token
    }
})
.then(res=>res.text())
.then(msg=>{
    alert(msg);

    document.getElementById("profilePreview").src="/images/defaultpic.png";
});

}
function openPaymentPage() {
    window.location.href = "/showIncomeDet.html";
}