package ru.sirosh.database.repositories;


import ru.sirosh.models.Comment;
import ru.sirosh.models.Post;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment> {
    public Comment findById(Long id);
    public Comment findByUserId(Long userId);
    public List<Comment> findByPostId(Long postId);

}
