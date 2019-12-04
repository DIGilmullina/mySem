package ru.sirosh.servlets.get;

import ru.sirosh.Init;
import ru.sirosh.ServletContextNames;
import ru.sirosh.database.repositories.*;
import ru.sirosh.models.Comment;
import ru.sirosh.models.Post;
import ru.sirosh.models.User;
import ru.sirosh.models.response.CommentView;
import ru.sirosh.models.response.DummyResp;
import ru.sirosh.models.response.PostMaxView;
import ru.sirosh.models.response.PostResp;
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

public class GetPost extends DummyServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("post get try");
        Map<String, String[]> params = req.getParameterMap();
        PrintWriter pw = resp.getWriter();
        Connection dbconnection = (Connection) getServletContext().getAttribute(ServletContextNames.DBCONNECTION.toString());   //начало операций с бд
        UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(dbconnection);
        PostRepositoryJdbcImpl prji = new PostRepositoryJdbcImpl(dbconnection);
        CommentRepositoryJdbcImpl crji = new CommentRepositoryJdbcImpl(dbconnection);
        CommentLikeRepositoryJdbcImpl clrji = new CommentLikeRepositoryJdbcImpl(dbconnection);
        PostLikeRepositoryJdbcImpl plrji = new PostLikeRepositoryJdbcImpl(dbconnection);

        User reqUser = null;
        if (req.getSession().getAttribute("userId") !=null){
            reqUser = urji.findOneById((Long) req.getSession().getAttribute("userId"));
        }

        if (params.containsKey("id") && !params.get("id")[0].equals("")) {
            Post post = prji.findById(Long.valueOf(params.get("id")[0]));
            User author = urji.findOneById(post.getId());
            boolean isAuthor = reqUser !=null && author.getId().equals(reqUser.getId());
            boolean liked = reqUser !=null && plrji.getByIds(reqUser.getId(),Long.valueOf(params.get("id")[0])) != null;
            List<CommentView> commentViewList = new ArrayList<>();
            List<Comment> commentList = crji.findByPostId(post.getId());
            Long postLikes = plrji.getCountById(Long.valueOf(params.get("id")[0]));
            for (Comment comment : commentList) {
                User commentAuthor = urji.findOneById(comment.getAuthorId());
                boolean commentLiked = reqUser !=null && clrji.getByIds(reqUser.getId(),comment.getId()) !=null;
                boolean isCommentAuthor = reqUser !=null &&  commentAuthor.getId().equals(reqUser.getId());
                String authorName = commentAuthor.getUsername();
                Long likes = clrji.getCountById(comment.getId());
                commentViewList.add(new CommentView(authorName, comment.getText(), isCommentAuthor, commentLiked, likes,comment.getId()));
            }
            pw.println(mapper.writeValueAsString(new PostResp("ok", new PostMaxView(post.getName(), post.getText(), author.getUsername(), isAuthor, liked,post.getId(),postLikes, null, commentViewList))));
        } else {
            pw.println(mapper.writeValueAsString(new DummyResp("err")));
        }
    }
}
