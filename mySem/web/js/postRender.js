const comment_part_1 = '            <br>\n' +
    '            <div class="panel panel-default col-lg-10 col-lg-offset-1">\n' +
    '                <div style="width: 100%; margin-top: 10px">\n' +
    '                    <div style="float:right; padding-right: 10px">\n' +
    '<button type="button" class="btn  btn-default" aria-label="Left Align" onclick="commentLike(this,';
const comment_part_1_2 = //тут id
    '                        )">\n' +
    '                            <i class="glyphicon glyphicon-heart';
const comment_part_1_6 = '-empty';
const comment_part_1_5 = ' pull-left" aria-hidden="true"></i><div style="float:right; padding-left: 10px">';
const comment_part_1_1 = //тут колличество лайков
    '                        </div></button>\n' +
    '                    </div>\n';
const comment_part_1_3 =
    '                    <div style="float:right; padding-right: 10px">\n' +
    '                        <button type="button" class="btn btn-default" onclick="commentDelete(this,';
const comment_part_1_7 =
    ')"><span\n' +
    '                                class="glyphicon glyphicon-remove"></span></button>\n' +
    '                    </div>\n' /*+
    '                    <div style="float:right; padding-right: 10px">\n' +
    '                        <button type="button" class="btn btn-default"><span\n' +
    '                                class="glyphicon glyphicon-pencil"></span></button>\n' +
    '                    </div>\n'*/;
const comment_part_1_4 =
    '                </div>\n' +
    '                <a href="profile.html"><h4>';
const comment_part_2 = //тут автор
    '</h4></a>\n' +
    '                <hr>\n' +
    '                <a style="color: #7b7b7b" href="contacts.html"><p>';
const comment_part_3 = //тут текст
    '</p>\n' +
    '                </a>\n' +
    '                <br>\n' +
    '            </div>\n' +
    '            <br>';


function postRender() {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    document.getElementById("send_comment").action = "addComment?postId=" + id;
    console.log('try get post');

    $.post('getPost', {
            'id': id,
        }, function (data) {

            let parsedJson = JSON.parse(data.toString());
            console.log(parsedJson);

            if (parsedJson.state === "ok") {
                document.getElementById("post_header").innerText = parsedJson.post.header;
                document.getElementById("post_author_href").innerText = parsedJson.post.authorName;
                document.getElementById("post_author_href").href = "profile.html?user=" + parsedJson.post.authorName;
                let deleteButton = document.getElementById("delete_button");
                if (parsedJson.post.isAuthor){
                    deleteButton.onclick=postDelete(0,parsedJson.post.id);
                }
                let likeButton = document.getElementById("like_button");
                likeButton.onclick = function () {
                    postLike(likeButton,parsedJson.post.id);
                };
                if (parsedJson.post.isLiked){
                    likeButton.parentNode.firstElementChild.firstElementChild.classList.remove('glyphicon-heart-empty');
                    likeButton.parentNode.firstElementChild.firstElementChild.classList.add('glyphicon-heart');
                }

                likeButton.childNodes.item(3).innerText = parsedJson.post.likes;
                console.log(likeButton.childNodes);

                if (parsedJson.post.url == null) {
                    document.getElementById("post_img").style.display = "none";
                } else {
                    document.getElementById("post_img").src = parsedJson.post.url;
                }
                document.getElementById("post_text").innerText = parsedJson.post.text;
                const commentContainer = document.getElementById("comments_container");
                commentContainer.innerHTML = "";
                parsedJson.post.comments.forEach(c => {

                    if (c.isAuthor) {
                        if (c.isLiked) {
                            commentContainer.innerHTML += comment_part_1 +
                                c.id +
                                comment_part_1_2 +
                                comment_part_1_5 +
                                c.likes +
                                comment_part_1_1 +
                                comment_part_1_3 +
                                c.id +
                                comment_part_1_7 +
                                comment_part_1_4 +
                                c.authorName +
                                comment_part_2 +
                                c.text +
                                comment_part_3;
                        } else {

                            commentContainer.innerHTML += comment_part_1 +
                                c.id +
                                comment_part_1_2 +
                                comment_part_1_6 +
                                comment_part_1_5 +
                                c.likes +
                                comment_part_1_1 +
                                comment_part_1_3 +
                                c.id +
                                comment_part_1_7 +
                                comment_part_1_4 +
                                c.authorName +
                                comment_part_2 +
                                c.text +
                                comment_part_3;
                        }
                    } else {
                        if (c.isLiked) {
                            commentContainer.innerHTML += comment_part_1 +
                                c.id +
                                comment_part_1_2 +
                                comment_part_1_5 +
                                c.likes +
                                comment_part_1_1 +
                                comment_part_1_4 +
                                c.authorName +
                                comment_part_2 +
                                c.text +
                                comment_part_3;
                        } else {
                            commentContainer.innerHTML += comment_part_1 +
                                c.id +
                                comment_part_1_2 +
                                comment_part_1_6 +
                                comment_part_1_5 +
                                c.likes +
                                comment_part_1_1 +
                                comment_part_1_4 +
                                c.authorName +
                                comment_part_2 +
                                c.text +
                                comment_part_3;
                        }
                    }

                });
            } else {
                document.location.href = '/';
            }
        }
    );
}