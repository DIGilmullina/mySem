package ru.sirosh.models.response;

public class LikeResp  extends DummyResp{
    public boolean like;

    public LikeResp(String state, boolean like) {
        super(state);
        this.like = like;
    }
}
