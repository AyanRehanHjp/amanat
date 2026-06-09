const token = localStorage.getItem("token");

window.onload = function(){

    showLoader();

    setTimeout(() => {

        fetch("/feedback/allFeedback",{
            headers:{
                "Authorization":"Bearer " + token
            }
        })
        .then(res => res.json())
        .then(data => {

            let tbody = document.querySelector("#feedbackTable tbody");
            tbody.innerHTML = "";

            data.forEach(f => {

                let row = `
                    <tr>
                        <td>${f.id}</td>
                        <td>${f.fullName}</td>
                        <td>${f.mobile}</td>
                        <td>${f.feedback}</td>
                    </tr>
                `;

                tbody.innerHTML += row;
            });

            hideLoader();

        })
        .catch(err => {

            hideLoader();

            alert("Failed to load feedback");
        });

    }, 100);
};

function goToAdmin(){
    window.location.href = "/admin.html";
}