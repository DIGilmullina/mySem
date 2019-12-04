function postLike(elem,id) {
    console.log(elem);
    $.post('likePost', {'postId': id}, function (data) {
            console.log(data);
            let parsedJson = JSON.parse(data.toString());
            if (parsedJson.state === "ok") {
                if (parsedJson.like){
                    elem.parentNode.firstElementChild.firstElementChild.classList.remove('glyphicon-heart-empty');
                    elem.parentNode.firstElementChild.firstElementChild.classList.add('glyphicon-heart');
                    elem.childNodes.item(3).innerText = parseInt(elem.childNodes.item(3).innerText) + 1;
                } else {
                    elem.parentNode.firstElementChild.firstElementChild.classList.remove('glyphicon-heart');
                    elem.parentNode.firstElementChild.firstElementChild.classList.add('glyphicon-heart-empty');
                    elem.childNodes.item(3).innerText = parseInt(elem.childNodes.item(3).innerText) -1;

                }
            } else if (parsedJson.state === "auth") {
                $('#modal_auth').modal();
                console.log('need Auth');

            } else if (parsedJson.state === "null") {
                console.log('ошибка номер X')
            }
        }
    );
}