package ru.sirosh.servlets.get;

import ru.sirosh.Init;
import ru.sirosh.ServletContextNames;
import ru.sirosh.database.repositories.CommentRepositoryJdbcImpl;
import ru.sirosh.database.repositories.PostRepositoryJdbcImpl;
import ru.sirosh.database.repositories.UsersRepositoryJdbcImpl;
import ru.sirosh.models.Comment;
import ru.sirosh.models.Post;
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
import java.util.List;
import java.util.Map;

public class GetComments extends DummyServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("comments get try");
        Map<String, String[]> params = req.getParameterMap();
        PrintWriter pw = resp.getWriter();
        Connection dbconnection = (Connection) getServletContext().getAttribute(ServletContextNames.DBCONNECTION.toString());   //начало операций с бд
        if (params.containsKey("postId")) {
            pw.println("{status:ok,");
            CommentRepositoryJdbcImpl crji = new CommentRepositoryJdbcImpl(dbconnection);
            List<Comment> comments = crji.findByPostId(Long.valueOf(params.get("postId")[0]));
            pw.println("comments:[");
            UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(dbconnection);
            for(Comment comment : comments){
                User author = urji.findOneById(comment.getAuthorId());
                pw.println("{author:'" + author.getUsername() +  "',");
                pw.println("text:'" + comment.getText() +  "'},");
            }
            pw.println("]}");
        } else {
            pw.println("{status:err}");
        }
    }
}
