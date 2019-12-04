package ru.sirosh.database.repositories;


import ru.sirosh.models.User;

public interface UsersRepository extends CrudRepository<User> {
    User findOneByUsername(String username);
    User findOneByMail(String mail);

}
