package ru.sirosh.servlets.edit;

import ru.sirosh.ServletContextNames;
import ru.sirosh.builders.CommentBuilder;
import ru.sirosh.database.repositories.CommentLikeRepositoryJdbcImpl;
import ru.sirosh.database.repositories.CommentRepositoryJdbcImpl;
import ru.sirosh.database.repositories.UsersRepositoryJdbcImpl;
import ru.sirosh.models.CommentLike;
import ru.sirosh.models.User;
import ru.sirosh.models.response.DummyResp;
import ru.sirosh.models.response.LikeResp;
import ru.sirosh.servlets.DummyServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Map;

public class LikeComment extends DummyServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> params = req.getParameterMap();
        PrintWriter pw = resp.getWriter();
        Connection dbconnection = (Connection) getServletContext().getAttribute(ServletContextNames.DBCONNECTION.toString());   //начало операций с бд
        CommentLikeRepositoryJdbcImpl clrji = new CommentLikeRepositoryJdbcImpl(dbconnection);
        UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(dbconnection);

        User reqUser = null;
        if (req.getSession().getAttribute("userId") != null) {
            reqUser = urji.findOneById((Long) req.getSession().getAttribute("userId"));
        }

        if (params.containsKey("commentId")) {
            if (reqUser == null) {
                pw.println(mapper.writeValueAsString(new DummyResp("auth")));
            } else {
                clrji.changeState(new CommentLike(Long.valueOf(params.get("commentId")[0]), reqUser.getId()));
                pw.println(mapper.writeValueAsString(new LikeResp("ok", clrji.getByIds(reqUser.getId(), Long.valueOf(params.get("commentId")[0])) != null)));
            }
        } else {
            pw.println(mapper.writeValueAsString(new DummyResp("err")));
        }
    }
}
