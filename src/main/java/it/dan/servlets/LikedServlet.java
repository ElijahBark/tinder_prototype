package it.dan.servlets;

import it.dan.dao.OpinionDAO;
import it.dan.dao.PersonDAO;
import it.dan.entities.Opinion;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.dan.AppRunner.userLogin;

public class LikedServlet extends HttpServlet {
    private FreeMarkerObject freeMarker;
    private Connection connection;

    public LikedServlet(FreeMarkerObject freeMarker, Connection connection) {
        this.connection = connection;
        this.freeMarker = freeMarker;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserCookies.download(req, connection);

        Map<String, Object> model = new HashMap<>();


        PersonDAO personDAO = new PersonDAO(connection);
        OpinionDAO opinionDAO = new OpinionDAO(connection);
        List<Opinion> opinions =  opinionDAO.getLikesByPerson(userLogin);
        List<Person> persons = new ArrayList<>();
        for (Opinion op: opinions) {
            Person person = personDAO.get(op.getWhom());
            persons.add(person);
        }
        model.put("items", persons);
        Writer out = resp.getWriter();

        freeMarker.run(model,"people-list.html", out, this.getClass());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
