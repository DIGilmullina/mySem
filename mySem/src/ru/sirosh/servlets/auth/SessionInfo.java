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

public class SessionInfo extends DummyServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        Connection dbconnection = (Connection) getServletContext().getAttribute(ServletContextNames.DBCONNECTION.toString());   //начало операций с бд
        Long userId = (Long) req.getSession().getAttribute("userId");
        User user = null;
        boolean wasInSession = true;
        System.out.println("session inf");
        if (userId == null){
            wasInSession = false;
            TokenRepositoryJdbcImpl trji = new TokenRepositoryJdbcImpl(dbconnection);
            Cookie[] cookies = req.getCookies();
            Token token = null;
            for (Cookie cookie : cookies  ) {
                if (cookie.getName().equals("token")){
                    token = trji.findByToken(cookie.getValue());
                    req.getSession().setAttribute("token", token.getToken());
                    req.getSession().setAttribute("userId", token.getUserId());
                    break;
                }
            }
            if (token != null){
                userId = token.getUserId();
            }
        }
        if (userId != null){
            UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(dbconnection);
            user = urji.findOneById(userId);
            if(!wasInSession){
                req.getSession().setAttribute("userId",user.getId());
            }
            pw.println("{\"state\" : \"ok\", \"username\" : \"" + user.getUsername() + "\"}");
        } else {
            pw.println("{\"state\" : \"null\", \"username\" : \"null\"}");
        }
    }
}
