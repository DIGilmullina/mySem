package ru.sirosh.servlets.delete;

import ru.sirosh.ServletContextNames;
import ru.sirosh.database.repositories.CommentRepositoryJdbcImpl;
import ru.sirosh.database.repositories.PostRepositoryJdbcImpl;
import ru.sirosh.database.repositories.UsersRepositoryJdbcImpl;
import ru.sirosh.models.Comment;
import ru.sirosh.models.Post;
import ru.sirosh.models.User;
import ru.sirosh.models.response.DummyResp;
import ru.sirosh.servlets.DummyServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Map;

public class deletePost extends DummyServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> params = req.getParameterMap();
        PrintWriter pw = resp.getWriter();
        Connection dbconnection = (Connection) getServletContext().getAttribute(ServletContextNames.DBCONNECTION.toString());   //начало операций с бд
        PostRepositoryJdbcImpl prji = new PostRepositoryJdbcImpl(dbconnection);
        UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(dbconnection);

        User reqUser = null;
        if (req.getSession().getAttribute("userId") != null) {
            reqUser = urji.findOneById((Long) req.getSession().getAttribute("userId"));
        }

        if (params.containsKey("postId")) {
            Post post = prji.findById(Long.valueOf(params.get("postId")[0]));
            if (reqUser != null && reqUser.getId().equals(post.getAuthorId())) {
                prji.delete(post);
                pw.println(mapper.writeValueAsString(new DummyResp("ok")));
            } else {
                pw.println(mapper.writeValueAsString(new DummyResp("perm_err")));
            }
        } else {
            pw.println(mapper.writeValueAsString(new DummyResp("err")));
        }
    }
}
