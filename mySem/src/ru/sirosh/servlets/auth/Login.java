package ru.sirosh.servlets.auth;

import ru.sirosh.Init;
import ru.sirosh.ServletContextNames;
import ru.sirosh.TokenGenerator;
import ru.sirosh.database.repositories.TokenRepositoryJdbcImpl;
import ru.sirosh.database.repositories.UsersRepositoryJdbcImpl;
import ru.sirosh.models.Token;
import ru.sirosh.models.User;
import ru.sirosh.servlets.DummyServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Map;
import java.util.Set;

public class Login extends DummyServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("login try");
        Map<String, String[]> params = req.getParameterMap();
        PrintWriter pw = resp.getWriter();
        Connection dbconnection = (Connection) getServletContext().getAttribute(ServletContextNames.DBCONNECTION.toString());   //начало операций с бд
        if (params.containsKey("username") && params.containsKey("password") && params.containsKey("remember_me") ){

            UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(dbconnection);
            String username = params.get("username")[0];//проверка наличия логина и пароля
            User user = urji.findOneByUsername(username);
            if (user == null){
                pw.println("{\"state\" : \"err_login\"}");
            } else {
                if (user.getPassword().equals(params.get("password")[0])){
                    req.getSession().setAttribute("userId", user.getId());
                    Token token = new Token(TokenGenerator.getInstance().getToken(), user.getId());
                    TokenRepositoryJdbcImpl trji = new TokenRepositoryJdbcImpl(dbconnection);
                    trji.save(token);
                    req.getSession().setAttribute("token", token.getToken());
                    if (Boolean.parseBoolean(params.get("remember_me")[0])){
                        resp.addCookie(new Cookie("token", token.getToken()));
                    }
                    pw.println("{\"state\" : \"ok\"}");
                } else {
                    pw.println("{\"state\" : \"err_login\"}");
                }
            }
        }else {
            pw.println("{\"state\" : \"err\"}");
        }
    }
}
