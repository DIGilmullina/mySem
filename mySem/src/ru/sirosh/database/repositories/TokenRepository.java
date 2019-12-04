package ru.sirosh.database.repositories;


import ru.sirosh.models.Token;
import ru.sirosh.models.User;

public interface TokenRepository extends CrudRepository<Token> {
    public Token findByToken(String token);
    public Token findById(String userId);

}
