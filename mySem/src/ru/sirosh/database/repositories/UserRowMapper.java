package ru.sirosh.database.repositories;

import ru.sirosh.builders.UserBuilder;
import ru.sirosh.database.repositories.RowMapper;
import ru.sirosh.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet row) throws SQLException {
        return UserBuilder.anUser()
                .withId(row.getLong("id"))
                .withUsername(row.getString("username"))
                .withPassword(row.getString("password"))
                .withBirthDate(row.getTimestamp("birt_date"))
                .withRegDate(row.getTimestamp("reg_date"))
                .withAbout(row.getString("about"))
                .withAvatar(row.getString("avatar"))
                .build();
    }
}
