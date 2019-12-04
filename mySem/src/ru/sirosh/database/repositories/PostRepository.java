package ru.sirosh.database.repositories;


import ru.sirosh.models.Post;
import ru.sirosh.models.Token;

import java.util.List;

public interface PostRepository extends CrudRepository<Post> {
    public Post findById(Long id);
    public Post findByUserId(Long userId);
    public Long postCount();
    public List<Post> findByPage(Long pageSize, Long page,int sortType);
}
