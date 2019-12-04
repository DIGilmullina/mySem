package ru.sirosh.database;

import java.sql.*;

public class DBConnection {
    private DBProperty dbprop;
    public  DBConnection(DBProperty dbprop){
        this.dbprop = dbprop;
    }
    public Connection connect() throws SQLException {
        Connection conn = DriverManager.getConnection(dbprop.url, dbprop.props);
        return conn;
    }
}
