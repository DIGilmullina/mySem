package ru.sirosh.servlets.edit;

import ru.sirosh.Init;
import ru.sirosh.servlets.DummyServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditInfo extends DummyServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameterMap().keySet());
        super.doPost(req, resp);
    }
}