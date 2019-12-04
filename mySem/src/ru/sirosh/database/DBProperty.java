package ru.sirosh.database;

import java.util.Properties;

public class DBProperty {
    Properties props;
    String url;
    String user;
    String password;

    public DBProperty(String url, String user, String password) {
        this.props = new Properties();
        this.user = "gilmullinadiana";
        this.password = "233522";
        this.url = "jdbc:postgresql://localhost:5432/postgres/dataa";
    }

}
