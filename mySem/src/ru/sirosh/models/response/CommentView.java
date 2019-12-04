package ru.sirosh.models.response;

public class CommentView {
    public String authorName;
    public String text;
    public boolean isAuthor;
    public boolean isLiked;
    public Long likes;
    public Long id;

    public CommentView(String authorName, String text, boolean isAuthor, boolean isLiked,Long likes,Long id) {
        this.authorName = authorName;
        this.text = text;
        this.isAuthor = isAuthor;
        this.isLiked = isLiked;
        this.likes=likes;
        this.id = id;
    }
}
