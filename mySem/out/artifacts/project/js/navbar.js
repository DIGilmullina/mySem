

function navUpdate() {
    const notReg = document.getElementById("not_reg_nav");
    const regged = document.getElementById("reged_nav");
    const name = document.getElementById("dropdownMenuButton");
    const href = document.getElementById("profile_href");
    const addPost = document.getElementById("addPostButton");

    $.post('session_info', function (data) {
            console.log(data);
            let parsedJson = JSON.parse(data.toString());
            if (parsedJson.state === "ok"){
                notReg.style.display = "none";
                addPost.style.display = "block";
                regged.style.display = "block";
                name.innerHTML = parsedJson.username;
                href.href = "profile.html?user=" + parsedJson.username;
            } else if(parsedJson.state === "null") {
                notReg.style.display = "block";
                addPost.style.display = "none";
                regged.style.display = "none";
            }
        }
    );
}