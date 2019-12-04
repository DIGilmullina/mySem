package ru.sirosh.database.repositories;


import ru.sirosh.models.Token;
import ru.sirosh.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TokenRepositoryJdbcImpl implements TokenRepository {
    private Connection connection;
    private final String tableName = "token";

    public TokenRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    private RowMapper<Token> tokenRowMapper = rs ->
            new Token(
                    rs.getString("token"),
                    rs.getLong("userId")
            );


    @Override
    public void save(Token token) {
        String sqlQuery = "insert into "+tableName+"  (token, userId) values ( ?, ?);";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setString(1, token.getToken());
            stmt.setLong(2, token.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }



    @Override
    public void update(Token token) {

    }


    @Override
    public void delete(Token token) {

    }

    @Override
    public List<Token> findAll() {
        return null;
    }

    @Override
    public Token findByToken(String token) {
        String sqlQuery = "SELECT * FROM "+tableName+"  WHERE token = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setString(1, String.valueOf(token));
            try (ResultSet rs = stmt.executeQuery()) {
                Token t = null;
                if (rs.next()) {
                    t = tokenRowMapper.mapRow(rs);
                }
                return t;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Token findById(String userId) {
        String sqlQuery = "SELECT * FROM "+tableName+"  WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setString(1, String.valueOf(userId));
            try (ResultSet rs = stmt.executeQuery()) {
                Token t = null;
                if (rs.next()) {
                    t = tokenRowMapper.mapRow(rs);
                }
                return t;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
