package ru.sirosh.servlets.add;

import ru.sirosh.Init;
import ru.sirosh.ServletContextNames;
import ru.sirosh.database.repositories.CommentRepositoryJdbcImpl;
import ru.sirosh.database.repositories.PostRepositoryJdbcImpl;
import ru.sirosh.builders.CommentBuilder;
import ru.sirosh.models.Post;
import ru.sirosh.models.response.DummyResp;
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

public class AddComment extends DummyServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("comment add try");
        Map<String, String[]> params = req.getParameterMap();
        PrintWriter pw = resp.getWriter();
        Connection dbconnection = (Connection) getServletContext().getAttribute(ServletContextNames.DBCONNECTION.toString());   //начало операций с бд
        Long userId = (Long) req.getSession().getAttribute("userId");

        PostRepositoryJdbcImpl prji = new PostRepositoryJdbcImpl(dbconnection);
        Post post = null;
        if (params.containsKey("postId") && params.containsKey("text")){
            post = prji.findById(Long.valueOf(params.get("postId")[0]));
            if (post == null){
                pw.println(mapper.writeValueAsString(new DummyResp("err")));
            }else {

                CommentRepositoryJdbcImpl crji  = new CommentRepositoryJdbcImpl(dbconnection);
                crji.save(CommentBuilder.aComment().withPostId(post.getId()).withAuthorId(userId).withText(params.get("text")[0]).build());
                resp.sendRedirect("/post.html?id=" + params.get("postId")[0]);
            }
        } else {
            pw.println(mapper.writeValueAsString(new DummyResp("err")));
        }

        System.out.println(params.keySet());



    }
}
