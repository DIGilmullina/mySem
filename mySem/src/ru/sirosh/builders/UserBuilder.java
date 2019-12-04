package ru.sirosh.builders;

import ru.sirosh.models.Permissions;
import ru.sirosh.models.User;

import java.sql.Timestamp;

public final class UserBuilder {
    private String username;
    private String password;
    private String about;
    private String country;
    private Timestamp birthDate;
    private Timestamp regDate;
    private Permissions permission;
    private Long id;
    private String email;
    private String avatar;

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }


    public UserBuilder withCountry(String country) {
        this.country = country;
        return this;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withAbout(String about) {
        this.about = about;
        return this;
    }

    public UserBuilder withBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public UserBuilder withRegDate(Timestamp regDate) {
        this.regDate = regDate;
        return this;
    }

    public UserBuilder withPermission(Permissions permission) {
        this.permission = permission;
        return this;
    }

    public UserBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public User build() {
        User user = new User();
        user.setCountry(country);
        user.setUsername(username);
        user.setPassword(password);
        user.setAbout(about);
        user.setBirthDate(birthDate);
        user.setRegDate(regDate);
        user.setPermission(permission);
        user.setId(id);
        user.setEmail(email);
        user.setAvatar(avatar);
        return user;
    }
}
