package ru.sirosh.models.response;

import ru.sirosh.models.Comment;

import java.util.List;

public class PostMaxView extends PostMinView {
    public String url;
    public List<CommentView> comments;

    public PostMaxView(String header, String text, String authorName, boolean isAuthor, boolean isLiked, Long id, Long likes, String url, List<CommentView> comments) {
        super(header, text, authorName, isAuthor, isLiked, id,likes);
        this.url = url;
        this.comments = comments;
    }
}
