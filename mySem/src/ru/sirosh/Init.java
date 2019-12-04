package ru.sirosh;

import ru.sirosh.database.DBConnection;
import ru.sirosh.database.DBProperty;

import javax.servlet.ServletContext;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Init  {
    private static Init instance = null;
    private  Connection dBConnection;

    public static Init getInstance(ServletContext servletContext) {
        if (instance == null){
            instance=new Init();
            try {
                System.out.println("data base configs :");
                String dbusername = (String) servletContext.getInitParameter("dbuser");
                String dbpassword = (String) servletContext.getInitParameter("dbpassword");
                String dburl = (String) servletContext.getInitParameter("dburl");
                System.out.println("user = " + dbusername);
                System.out.println("password = " + dbpassword);
                System.out.println("dburl = " + dburl);

                DBProperty dbProperty = new DBProperty(dburl, dbusername, dbpassword);
                DriverManager.registerDriver (new com.mysql.jdbc.Driver());
                instance.dBConnection = new DBConnection(dbProperty).connect();
                System.out.println("connected to database");
                servletContext.setAttribute(ServletContextNames.DBCONNECTION.toString(), instance.dBConnection);
            } catch (SQLException e) {
                throw new IllegalArgumentException("Can't connect to database | Не удалось подключится к базе данных | " + e.getMessage());
            }
        }
        return instance;
    }

    private Init() {
    }
}
