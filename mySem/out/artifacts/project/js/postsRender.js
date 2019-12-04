const post_p1 =
    '        <br>\n' +
    '        <div class="panel panel-default col-lg-10 col-lg-offset-1">\n' +
    '            <div style="width: 100%; margin-top: 10px">\n' +
    '               <div style="float:right; padding-right: 10px">\n' +
    '                    <button type="button" class="btn btn-default" aria-label="Left Align" onclick="postLike(this,';
const post_p1_2 =
    ')">\n' +
    '                        <span class="glyphicon glyphicon-heart';
const post_p1_3 =
    '-empty';
const post_p1_4 =
    '" aria-hidden="true"></span>' +
    '<div style="float:right; padding-left: 10px">';
const post_p1_5 =
    '</div>' +
    '                    </button>\n' +
    '                </div>\n';
const post_p1_8 =
    '                <div style="float:right; padding-right: 10px">\n' +
    '                    <button type="button" class="btn btn-default" onclick="postDelete(this,';
const post_p1_6 =
    ')"><span\n' +
    '                            class="glyphicon glyphicon-remove"></span></button>\n' +
    '                </div>\n';/*+
     '                <div style="float:right; padding-right: 10px">\n' +
     '                    <button type="button" class="btn btn-default"><span\n' +
     '                            class="glyphicon glyphicon-pencil"></span></button>\n' +
     '                </div>' +*/
const post_p1_7 =
    '            </div>\n' +
    '            <a href="profile.html?user=';
const post_p1_1 = '"><h4>Автор: '; //ссылка на профайл автора
const post_p2 = //тут имя пользователя
    '</h4></a >\n' +
    '            <hr>\n' +
    '            <a href="post.html?id=';
const post_p2_1 = '"><h4>';
const post_p3 =//тут должна быть тема
    '</h4></a>\n' +
    '            <hr>\n' +
    '            <a style="color: #7b7b7b" href="post.html?id=';
const post_p3_1 = '"><p>'; //ссылка на полный пост
const post_p4 = //текст
    '            </p>\n' +
    '            </a>\n' +
    '            <br>\n' +
    '        </div>\n' +
    '        <br>';


function postsRender(sort) {
    const urlParams = new URLSearchParams(window.location.search);
    const page = urlParams.get('page') == null ? 1 : urlParams.get('page');

    const container = document.getElementById("post_containers");
    console.log(sort);
    console.log('try get posts');

    $.post('getPosts', {
            'page': page,
            'sort': sort
        }, function (data) {
            let posts = '';

            console.log(data);
            let parsedJson = JSON.parse(data.toString());
            if (parsedJson.state === "ok") {
                document.getElementById("pageIndicator").innerText = page + '/' + Math.ceil(parsedJson.count / 10);
                forwardButton = document.getElementById('button_forward');
                backwardButton = document.getElementById('button_back');
                if (Math.ceil(parsedJson.count / 10) === page) {
                    forwardButton.disabled = true;
                } else {
                    forwardButton.onclick = function () {
                        if (sort === 0)
                            document.location.href = '/fresh.html?page=' + (parseInt(page) + 1);
                        else if (sort === 1)
                            document.location.href = '/fresh.html?page=' + (parseInt(page) + 1);
                    }
                }
                if (1 === page) {
                    backwardButton.disabled = true;
                } else {
                    backwardButton.onclick = function () {
                        if (sort === 0)
                            document.location.href = '/fresh.html?page=' + (parseInt(page) - 1);
                        else if (sort === 1)
                            document.location.href = '/fresh.html?page=' + (parseInt(page) - 1);
                    }
                }
                container.innerHTML = "";
                parsedJson.posts.forEach(post => {
                    container.innerHTML += post_p1 +
                        post.id +
                        post_p1_2 +
                        (post.isLiked ? "" : post_p1_3) +
                        post_p1_4 +
                        post.likes +
                        post_p1_5 +
                        (post.isAuthor ? post_p1_8 + post.id + post_p1_6 : "") +
                        post_p1_7 +
                        post.authorName +
                        post_p1_1 +
                        post.authorName +
                        post_p2 +
                        post.id +
                        post_p2_1 +
                        post.header +
                        post_p3 +
                        post.id +
                        post_p3_1 +
                        post.text +
                        post_p4;
                });
            } else {
                document.location.href = '/';
            }
        }
    );
}