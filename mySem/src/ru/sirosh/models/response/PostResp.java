package ru.sirosh.models.response;

import ru.sirosh.models.Post;

public class PostResp extends DummyResp{
    public PostMaxView post;

    public PostResp(String state, PostMaxView post) {
        super(state);
        this.post = post;
    }
}
