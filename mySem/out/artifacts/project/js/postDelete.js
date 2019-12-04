function postDelete(elem, id) {
    $.post('postDelete', {'postId': id}, function (data) {
            console.log(data);
            let parsedJson = JSON.parse(data.toString());
            if (parsedJson.state === "ok") {
                window.location.href = window.location.pathname + window.location.search + window.location.hash;
            }
        }
    );
}