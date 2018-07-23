package it.dan.servlets;

import it.dan.AppRunner;
import it.dan.dao.PersonDAO;
import it.dan.entities.Person;
import it.dan.utilits.PersonsForUser;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;


public class LoginServlet extends HttpServlet {
    private Connection connection;

    public LoginServlet(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String out = FileUtils.readFileToString(new File("resources/login.html"),"UTF-8");
        resp.getWriter().print(out);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("pass");

        PersonDAO personDAO = new PersonDAO(connection);
        Person user = personDAO.get(login);
        if (user != null && user.getPassword().equals(password)) {
            AppRunner.userLogin = login;
            AppRunner.persons = PersonsForUser.download(login, connection);

            Cookie name = new Cookie("userid", login);
            Cookie pass = new Cookie("pass", password);
            name.setMaxAge(300); // lifetime in seconds
            pass.setMaxAge(300); // lifetime in seconds
            resp.addCookie(name);
            resp.addCookie(pass);

            resp.sendRedirect("/users");
        } else {
            doGet(req,resp);
        }

    }


}
