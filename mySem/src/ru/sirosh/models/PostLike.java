package ru.sirosh.models;

public class PostLike {
    private Long id;
    private  Long postId;
    private Long userId;

    public PostLike(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public PostLike(Long id, Long postId, Long userId) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
