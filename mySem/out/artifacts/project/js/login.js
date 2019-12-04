const submitButton = document.getElementById("submit");
const error = document.getElementById("error");
const username = document.getElementById("username");
const password = document.getElementById("password");
const remember_me = document.getElementById("remember_me");


submitButton.onclick = function () {
    $.post('login',{
            'username' : username.value,
            'password': password.value,
            'remember_me': remember_me.checked
        }, function (data) {
            console.log(data);
            let parsedJson = JSON.parse(data.toString(), function (k, v) {
                if (k === "state" && v === "ok"){
                    document.location.href = '/';
                } else if (k === "state" && v === "err_login"){
                    error.innerHTML = "Неверный логин или пароль";
                    error.style.display = "block";
                }
            });
        }
    );
};