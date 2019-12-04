package ru.sirosh.models.response;

import java.util.List;

public class PostsResp  extends DummyResp{
    public List<PostMinView> posts;
    public Long count;
    public PostsResp(String state, List<PostMinView> posts,Long count) {
        super(state);
        this.posts = posts;
        this.count = count;
    }

}
