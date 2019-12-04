package ru.sirosh.database.repositories;

import ru.sirosh.models.PostLike;

public interface PostLikeRepository extends CrudRepository<PostLike> {
    public PostLike getByIds(Long userId, Long commentId);
    public Long getCountById(Long commentId);
    public void changeState(PostLike commentLike);
}
