package ru.sirosh.database.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    void save(T t);
    void update(T t);
    void delete(T t);

    List<T> findAll();
}
