package it.dan.servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import it.dan.dao.MessageDAO;
import it.dan.dao.PersonDAO;
import it.dan.Utilits;
import it.dan.entities.Message;
import it.dan.entities.Person;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import static it.dan.AppRunner.persons;
import static it.dan.AppRunner.userLogin;

public class ChatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utilits.downloadCookies(req);

        String login = req.getParameter("login");
        Map<String, Object> model = new HashMap<>();

        PersonDAO personDAO = new PersonDAO();
        Person person = personDAO.get(login);
        if (person == null) {
           resp.sendRedirect("/liked");
        } else {
            model.put("ans",person);
        }


        MessageDAO messageDAO = new MessageDAO();
        List<Message> messages = messageDAO.getConversation(userLogin,login);
        Collections.sort(messages);
        model.put("messages", messages);

        Writer out = resp.getWriter();

        Utilits.freeMarkerRun(model,"chat.html", out);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("text-message");
        text = text.equals("")? null: text;
        String login = req.getParameter("login");
        Message message = new Message(text, userLogin, login);
        MessageDAO messageDAO = new MessageDAO();
        messageDAO.save(message);
        doGet(req,resp);
    }
}
