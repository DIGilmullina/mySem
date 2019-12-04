package ru.sirosh.servlets.auth;

import ru.sirosh.Init;
import ru.sirosh.ServletContextNames;
import ru.sirosh.TokenGenerator;
import ru.sirosh.database.repositories.TokenRepositoryJdbcImpl;
import ru.sirosh.database.repositories.UsersRepositoryJdbcImpl;
import ru.sirosh.models.Token;
import ru.sirosh.models.User;
import ru.sirosh.builders.UserBuilder;
import ru.sirosh.servlets.DummyServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

public class Register extends DummyServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("registration try");
        Map<String, String[]> params = req.getParameterMap();
        Set paramsKeys = params.keySet();
        PrintWriter pw = resp.getWriter();

        Connection dbconnection = (Connection) getServletContext().getAttribute(ServletContextNames.DBCONNECTION.toString());   //начало операций с бд
        if (params.keySet().contains("username") && paramsKeys.contains("email") && paramsKeys.contains("password")){
            UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(dbconnection);
            String username = params.get("username")[0];                                                                        //проверка наличия логина и пароля
            String mail = params.get("email")[0];
            boolean existWithName = null != urji.findOneByUsername(username);
            boolean existWithMail = null != urji.findOneByMail(mail);

            if (!existWithMail && !existWithName){
                String password = params.get("password")[0];
                User user = UserBuilder.anUser().withUsername(username).withPassword(password).withEmail(mail).withRegDate(new Timestamp(System.currentTimeMillis())).build();
                urji.save(user);

                req.getSession().setAttribute("userId", user.getId());
                Token token = new Token(TokenGenerator.getInstance().getToken(), user.getId());
                TokenRepositoryJdbcImpl trji = new TokenRepositoryJdbcImpl(dbconnection);
                trji.save(token);
                req.getSession().setAttribute("token", token.getToken());
                resp.addCookie(new Cookie("token", token.getToken()));

                pw.println("{\"state\" : \"ok\"}");
            } else {
                if (existWithMail && existWithName){
                    pw.println("{\"state\" : \"err_mail_name\"}");
                }else if (existWithMail){
                    pw.println("{\"state\" : \"err_mail\"}");
                }else pw.println("{\"state\" : \"err_name\"}");
            }
        }else {
            pw.println("{\"state\" : \"err\"}");
        }
    }

}
