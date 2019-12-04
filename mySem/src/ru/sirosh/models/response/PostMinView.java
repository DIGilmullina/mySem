package ru.sirosh.models.response;

public class PostMinView {
    public String header;
    public String text;
    public String authorName;
    public boolean isAuthor;
    public boolean isLiked;
    public Long id;
    public Long likes;

    public PostMinView(String header, String text, String authorName, boolean isAuthor, boolean isLiked, Long id, Long likes) {
        this.id = id;
        this.header = header;
        this.text = text;
        this.authorName = authorName;
        this.isAuthor = isAuthor;
        this.isLiked = isLiked;
        this.likes = likes;
    }
}
