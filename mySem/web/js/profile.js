function dataSet() {
    const urlParams = new URLSearchParams(window.location.search);
    const profile_name = urlParams.get('user');
    $.post('profile', {
            'user': profile_name
        }, function (data) {
            console.log(data);
            let parsedJson = JSON.parse(data.toString());
            if (parsedJson.state === "ok") {
                if (parsedJson.user_exist === 'true') {
                    const form_1 = document.getElementById("profile_form_1");
                    const form_2 = document.getElementById("profile_form_2");
                    const form_3 = document.getElementById("profile_form_3");
                    const info_div = document.getElementById("info_div");
                    const profile_username = document.getElementById("profile_username");
                    profile_username.innerHTML = profile_name;
                    if (parsedJson.user_owner === 'true') {
                        form_1.style.display = "block";
                        form_2.style.display = "block";
                        form_3.style.display = "block";
                        info_div.style.display = "none";

                        document.getElementById("profile_about_input").value = parsedJson.profile_about;
                        document.getElementById("profile_reg_date_input").value = parsedJson.reg_date;
                        document.getElementById("profile_date_input").value = parsedJson.birth_date;
                        document.getElementById("profile_country_input").value = parsedJson.profile_country;
                        //если юзер владелец то form видимый
                    }
                    if (parsedJson.user_owner === 'false'){
                        form_1.style.display = "none";
                        form_2.style.display = "none";
                        form_3.style.display = "none";
                        info_div.style.display = "block";

                        document.getElementById("profile_about_info").innerHTML = parsedJson.profile_about;;
                        document.getElementById("profile_reg_date_info").innerHTML = parsedJson.reg_date;
                        document.getElementById("profile_date_info").innerHTML = parsedJson.birth_date;
                        document.getElementById("profile_country_info").innerHTML  = parsedJson.profile_country;
                        //если чужая страница
                    }
                } else {
                    document.location.href = '/';
                }
            } else {
                document.location.href = '/';
            }
        }
    );
}