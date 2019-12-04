package ru.sirosh.servlets.auth;

import ru.sirosh.Init;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogOut extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        Init.getInstance(config.getServletContext());
        super.init(config);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("userId");
        req.getSession().removeAttribute("token");
        Cookie token = new Cookie("token", "");
        token.setMaxAge(-1);
        resp.addCookie(token);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
        resp.sendRedirect("/");
    }
}
