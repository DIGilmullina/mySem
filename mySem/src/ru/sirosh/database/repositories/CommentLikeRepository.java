package ru.sirosh.database.repositories;

import ru.sirosh.models.CommentLike;

public interface CommentLikeRepository extends CrudRepository<CommentLike> {
    public CommentLike getByIds(Long userId, Long commentId);
    public Long getCountById(Long commentId);
    public void changeState(CommentLike commentLike);
}
