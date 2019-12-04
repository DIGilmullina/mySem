package ru.sirosh.models;

public class CommentLike {
    private Long id;
    private  Long commentId;
    private Long userId;

    public CommentLike(Long commentId, Long userId) {
        this.commentId = commentId;
        this.userId = userId;
    }

    public CommentLike(Long id, Long commentId, Long userId) {
        this.id = id;
        this.commentId = commentId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
