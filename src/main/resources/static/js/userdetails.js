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
const role  = localStorage.getItem("role");

if(!token || role.toUpperCase() !== "USER"){
    window.location.href="/login.html";
}

/* FETCH USER */
function loadUser(){

const userId = localStorage.getItem("userId");

fetch(RETRIEVE_USER  + userId,{
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
    memberId.value = data.memberId || (data.member && data.member.memberId) || "";

    /*  PROFILE IMAGE LOAD WITH AUTH HEADER + SPACE FIX */
   if(data.profilePicture){
       document.getElementById("profilePreview").src = data.profilePicture;
   }else{
       document.getElementById("profilePreview").src = "/images/defaultpic.png";
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

fetch(UPDATE_USER +userId,{
    method:"PUT",
    headers:{
        "Authorization":"Bearer "+token
    },
    body:formData
})
.then(res=>res.text())
.then(msg=>{
    showPopup(msg);

    /* reload ki jagah direct latest image load */
    loadUser();
});

}


/* LOGOUT */
function logout(){
    localStorage.clear();
    window.location.href="/login.html";
}
function deleteProfilePic(){

const userId = localStorage.getItem("userId");

fetch(REMOVE_PROFILE_PIC+userId,{
    method:"DELETE",
    headers:{
        "Authorization":"Bearer "+token
    }
})
.then(res=>res.text())
.then(msg=>{
    showPopup(msg);

    document.getElementById("profilePreview").src="/images/defaultpic.png";
});

}
function openPaymentPage() {
    window.location.href = "/showIncomeDet.html?type=my";
}
// Redirect to personal report page
function openPaymentPage(){

    // Adding ?type=my so frontend knows this is user-specific request
    window.location.href = "/showIncomeDet.html?type=my";
}