package ru.sirosh.servlets.get;

import ru.sirosh.Init;
import ru.sirosh.ServletContextNames;
import ru.sirosh.database.repositories.UsersRepositoryJdbcImpl;
import ru.sirosh.models.User;
import ru.sirosh.servlets.DummyServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Map;

public class GetProfile extends DummyServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("profile get try");
        Map<String, String[]> params = req.getParameterMap();
        Connection dbconnection = (Connection) getServletContext().getAttribute(ServletContextNames.DBCONNECTION.toString());   //начало операций с бд
        PrintWriter pw = resp.getWriter();

        if (params.containsKey("user")){
            pw.println("{");
            pw.println("\"state\" : \"ok\",");
            UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(dbconnection);
            String username = params.get("user")[0];//проверка наличия логина и пароля
            User user = urji.findOneByUsername(username);
            if (user == null){
                pw.println("\"user_exist\" : \"false\"");
            }else {
                pw.println("\"user_exist\" : \"true\",");

                Long userId = (Long) req.getSession().getAttribute("userId");
                User askingUser = null;
                if (userId!= null){
                    askingUser = urji.findOneById(userId);
                }
                if (askingUser != null && askingUser.getUsername().equals(username)){
                    pw.println("\"user_owner\" : \"true\",");
                }else{
                    pw.println("\"user_owner\" : \"false\",");
                }
                pw.println("\"profile_about\" : \"about\",");
                pw.println("\"reg_date\" : \"reg date\",");
                pw.println("\"birth_date\" : \" birth date\",");
                pw.println("\"profile_country\" : \"country\"");

            }
            pw.println("}");

        }else {
            pw.println("{\"state\" : \"err\"}");
        }

    }
}
