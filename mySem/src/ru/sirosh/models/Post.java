package ru.sirosh.models;

import java.sql.Blob;
import java.sql.Timestamp;

public class Post {
    private Long id;
    private String name;
    private String text;
    private String img;
    private Timestamp madeDate;
    private Long authorId;

    public Post() {
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Timestamp getMadeDate() {
        return madeDate;
    }

    public void setMadeDate(Timestamp madeDate) {
        this.madeDate = madeDate;
    }
}
