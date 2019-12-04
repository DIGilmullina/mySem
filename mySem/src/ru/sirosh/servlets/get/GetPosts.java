package ru.sirosh.servlets.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sirosh.Init;
import ru.sirosh.ServletContextNames;
import ru.sirosh.database.repositories.CommentLikeRepositoryJdbcImpl;
import ru.sirosh.database.repositories.PostLikeRepositoryJdbcImpl;
import ru.sirosh.database.repositories.PostRepositoryJdbcImpl;
import ru.sirosh.database.repositories.UsersRepositoryJdbcImpl;
import ru.sirosh.models.Post;
import ru.sirosh.models.User;
import ru.sirosh.models.response.DummyResp;
import ru.sirosh.models.response.PostMinView;
import ru.sirosh.models.response.PostsResp;
import ru.sirosh.servlets.DummyServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetPosts extends DummyServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("post get try");
        Map<String, String[]> params = req.getParameterMap();
        PrintWriter pw = resp.getWriter();
        Connection dbconnection = (Connection) getServletContext().getAttribute(ServletContextNames.DBCONNECTION.toString());   //начало операций с бд
        UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(dbconnection);
        PostRepositoryJdbcImpl prji = new PostRepositoryJdbcImpl(dbconnection);
        PostLikeRepositoryJdbcImpl plrji = new PostLikeRepositoryJdbcImpl(dbconnection);
        Long reqUserId = (Long) req.getSession().getAttribute("userId");
        User reqUser = null;
        System.out.println();
        if (reqUserId != null)
            reqUser = urji.findOneById(reqUserId);

        if (params.containsKey("page") && params.containsKey("sort")) {
            List<Post> posts = null;
            if (params.get("page")[0].equals(""))
                posts = prji.findByPage((long) 10, (long) 1,Integer.valueOf(params.get("sort")[0]));
            else
                posts = prji.findByPage((long) 10, Long.valueOf(params.get("page")[0]),Integer.valueOf(params.get("sort")[0]));
            List<PostMinView> list = new ArrayList<>();
            for (Post post : posts) {
                User author = urji.findOneById(post.getAuthorId());
                boolean isAuthor = reqUser != null && author.getId().equals(reqUser.getId());
                boolean liked = reqUser != null && plrji.getByIds(reqUser.getId(), post.getId()) != null;
                Long likes = plrji.getCountById(post.getId());
                list.add(new PostMinView(post.getName(), post.getText(), author.getUsername(), isAuthor, liked, post.getId(),likes));
            }
            pw.println(mapper.writeValueAsString(new PostsResp("ok", list,prji.postCount())));
        } else {
            pw.println(mapper.writeValueAsString(new DummyResp("err")));
        }
    }
}
