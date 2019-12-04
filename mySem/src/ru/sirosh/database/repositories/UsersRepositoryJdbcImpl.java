package ru.sirosh.database.repositories;


import ru.sirosh.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private Connection connection;

    private final String tableName = "user";
    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }



    public User findOneById(Long id) {
        String sqlQuery = "SELECT * FROM " + tableName + "  WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1,id);
            try (ResultSet rs = stmt.executeQuery()) {
                User u = null;
                if (rs.next()) {
                    u = new UserRowMapper().mapRow(rs);
                }
                return u;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public User findOneByMail(String mail) {
        String sqlQuery = "SELECT * FROM " + tableName + "  WHERE mail = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setString(1,String.valueOf(mail));
            try (ResultSet rs = stmt.executeQuery()) {
                User u = null;
                if (rs.next()) {
                    u = new UserRowMapper().mapRow(rs);
                }
                return u;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public User findOneByUsername(String username) {
        String sqlQuery = "SELECT * FROM "+tableName+"  WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setString(1, String.valueOf(username));
            try (ResultSet rs = stmt.executeQuery()) {
                User u = null;
                if (rs.next()) {
                    u = new UserRowMapper().mapRow(rs);
                }
                return u;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public void save(User user) {
        String sqlQuery = "insert into "+tableName+"  (username, password, mail) values ( ?, ?, ?);";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.executeUpdate();
            ResultSet rs = stmt.executeQuery("select LAST_INSERT_ID();");
            if (rs.next()){
                user.setId(rs.getLong("LAST_INSERT_ID()"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public List<User> findAll() {
        String sqlQuery = "SELECT * FROM " + tableName;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new UserRowMapper().mapRow(rs));
            }
            return users;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
