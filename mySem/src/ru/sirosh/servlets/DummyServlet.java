package ru.sirosh.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sirosh.Init;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class DummyServlet extends HttpServlet {
    protected ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        Init.getInstance(config.getServletContext());

        super.init(config);
    }

}
