package ru.sirosh.servlets.edit;

import ru.sirosh.ServletContextNames;
import ru.sirosh.database.repositories.PostLikeRepositoryJdbcImpl;
import ru.sirosh.database.repositories.PostRepositoryJdbcImpl;
import ru.sirosh.database.repositories.PostRepositoryJdbcImpl;
import ru.sirosh.database.repositories.UsersRepositoryJdbcImpl;
import ru.sirosh.models.PostLike;
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

public class LikePost extends DummyServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> params = req.getParameterMap();
        PrintWriter pw = resp.getWriter();
        Connection dbconnection = (Connection) getServletContext().getAttribute(ServletContextNames.DBCONNECTION.toString());   //начало операций с бд
        PostLikeRepositoryJdbcImpl plrji = new PostLikeRepositoryJdbcImpl(dbconnection);
        UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(dbconnection);

        User reqUser = null;
        if (req.getSession().getAttribute("userId") != null) {
            reqUser = urji.findOneById((Long) req.getSession().getAttribute("userId"));
        }

        if (params.containsKey("postId")) {
            if (reqUser == null) {
                pw.println(mapper.writeValueAsString(new DummyResp("auth")));
            } else {
                plrji.changeState(new PostLike(Long.valueOf(params.get("postId")[0]), reqUser.getId()));
                pw.println(mapper.writeValueAsString(new LikeResp("ok", plrji.getByIds(reqUser.getId(), Long.valueOf(params.get("postId")[0])) != null)));
            }
        } else {
            pw.println(mapper.writeValueAsString(new DummyResp("err")));
        }
    }
}
