package ru.sirosh.servlets.add;

import ru.sirosh.Init;
import ru.sirosh.ServletContextNames;
import ru.sirosh.database.repositories.PostRepositoryJdbcImpl;
import ru.sirosh.builders.PostBuilder;
import ru.sirosh.servlets.DummyServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Map;

//addPost
public class AddPost extends DummyServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("post add try");
        Map<String, String[]> params = req.getParameterMap();
        PrintWriter pw = resp.getWriter();
        Connection dbconnection = (Connection) getServletContext().getAttribute(ServletContextNames.DBCONNECTION.toString());   //начало операций с бд
        Long userId = (Long) req.getSession().getAttribute("userId");

        if (userId == null) {
            resp.sendRedirect("/login.html");
        } else if (params.containsKey("header") && params.containsKey("photo") && params.containsKey("text")) {
            PostRepositoryJdbcImpl prji = new PostRepositoryJdbcImpl(dbconnection);
            prji.save(PostBuilder.aPost().withAuthorId(userId).withText(params.get("text")[0]).withName(params.get("header")[0]).withMadeDate(new Timestamp(System.currentTimeMillis())).build());
            resp.sendRedirect("/");
        } else {
            resp.sendRedirect("/addPost.html");
        }

    }
}
