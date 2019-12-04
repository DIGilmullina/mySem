package ru.sirosh.builders;

import ru.sirosh.models.Comment;

public final class CommentBuilder {
    private String text;
    private Long id;
    private Long authorId;
    private Long postId;

    private CommentBuilder() {
    }

    public static CommentBuilder aComment() {
        return new CommentBuilder();
    }

    public CommentBuilder withText(String text) {
        this.text = text;
        return this;
    }

    public CommentBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CommentBuilder withAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public CommentBuilder withPostId(Long postId) {
        this.postId = postId;
        return this;
    }

    public Comment build() {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setId(id);
        comment.setAuthorId(authorId);
        comment.setPostId(postId);
        return comment;
    }
}
