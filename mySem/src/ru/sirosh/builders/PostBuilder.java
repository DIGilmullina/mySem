package ru.sirosh.builders;

import ru.sirosh.models.Post;

import java.sql.Timestamp;

public final class PostBuilder {
    private Long id;
    private String name;
    private String text;
    private String img;
    private Timestamp madeDate;
    private Long authorId;

    private PostBuilder() {
    }

    public static PostBuilder aPost() {
        return new PostBuilder();
    }

    public PostBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public PostBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public PostBuilder withText(String text) {
        this.text = text;
        return this;
    }

    public PostBuilder withImg(String img) {
        this.img = img;
        return this;
    }

    public PostBuilder withMadeDate(Timestamp madeDate) {
        this.madeDate = madeDate;
        return this;
    }

    public PostBuilder withAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public Post build() {
        Post post = new Post();
        post.setId(id);
        post.setName(name);
        post.setText(text);
        post.setImg(img);
        post.setMadeDate(madeDate);
        post.setAuthorId(authorId);
        return post;
    }
}
