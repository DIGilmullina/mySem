var username = document.getElementById("username");
var nameError = document.getElementById("name-exist");


var isEmailCorrect = false;
var email = document.getElementById("email");
var emailError = document.getElementById("email-error");
var emailExist = document.getElementById("email-exist");


var isPasswordCorrect = false;
var password = document.getElementById("password");
var passwordError = document.getElementById("password-error");

var submitButton = document.getElementById("submit");


email.oninput = function() {
   email.value = email.value.trim();
};
password.oninput = function() {
    var regExp = /^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]{8,32}$/g;
    if (!regExp.test(password.value)) {
        passwordError.innerHTML = "Пароль должен содержать только цифры и буквы латинского алфавита";
        passwordError.style.display = "block";
        isEmailCorrect = false;
    } else {
        passwordError.style.display = "none";
        isEmailCorrect = true;
    }
    buttonChgState();
};
email.oninput = function() {
    var regExp = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (!regExp.test(email.value.toLowerCase())) {
        emailError.innerHTML = "Email не корректный";
        emailError.style.display = "block";
        isPasswordCorrect = false;
    } else {
        emailError.style.display = "none";
        isPasswordCorrect = true;
    }
    buttonChgState();
};

submitButton.onclick = function () {
    $.post('registration',{
            'username' : username.value,
            'password': password.value,
            'email': password.value
        }, function (data) {
            console.log(data);

            let parsedJson = JSON.parse(data.toString(), function (k, v) {
                if (k == "state" && v == "ok"){
                    document.location.href = '/';
                } else if (k == "state" && v == "err_mail"){
                    emailError.innerHTML = "Данный email уже зарегестрирован";
                    emailError.style.display = "block";
                    emailError.style.display = "none";
                } else if (k == "state" && v == "err_name"){
                    nameError.innerHTML = "Ник уже занят";
                    nameError.style.display = "block";
                    emailError.style.display = "none";
                }else if (k == "state" && v == "err_mail_name"){
                    nameError.innerHTML = "Ник уже занят";
                    emailError.innerHTML = "Данный email уже зарегестрирован";
                    nameError.style.display = "block";
                    emailError.style.display = "block";
                }
            });
        }
    );
};

function buttonChgState() {
    if (isEmailCorrect && isPasswordCorrect) {
    submitButton.disabled = false;
} else submitButton.disabled = true;
}



