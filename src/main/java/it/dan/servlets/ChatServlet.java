package it.dan.servlets;

import it.dan.dao.MessageDAO;
import it.dan.dao.PersonDAO;
import it.dan.entities.Message;
import it.dan.entities.Person;
import it.dan.utilits.FreeMarkerObject;
import it.dan.utilits.UserCookies;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.util.*;

import static it.dan.AppRunner.userLogin;

public class ChatServlet extends HttpServlet {

    private FreeMarkerObject freeMarker;
    private Connection connection;

    public ChatServlet(FreeMarkerObject freeMarker, Connection connection) {
        this.connection = connection;
        this.freeMarker = freeMarker;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserCookies.download(req, connection);

        String login = req.getParameter("login");
        Map<String, Object> model = new HashMap<>();

        PersonDAO personDAO = new PersonDAO(connection);
        Person person = personDAO.get(login);
        if (person == null) {
           resp.sendRedirect("/liked");
        } else {
            model.put("ans",person);
        }


        MessageDAO messageDAO = new MessageDAO(connection);
        List<Message> messages = messageDAO.getConversation(userLogin,login);
        Collections.sort(messages);
        model.put("messages", messages);

        Writer out = resp.getWriter();

        freeMarker.run(model,"chat.html", out, this.getClass());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("text-message");
        text = text.equals("")? null: text;
        String login = req.getParameter("login");
        Message message = new Message(text, userLogin, login);
        MessageDAO messageDAO = new MessageDAO(connection);
        messageDAO.save(message);
        doGet(req,resp);
    }
}
