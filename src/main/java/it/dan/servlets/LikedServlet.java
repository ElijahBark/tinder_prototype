package it.dan.servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import it.dan.dao.OpinionDAO;
import it.dan.dao.PersonDAO;
import it.dan.Utilits;
import it.dan.entities.Opinion;
import it.dan.entities.Person;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.dan.AppRunner.persons;
import static it.dan.AppRunner.userLogin;

public class LikedServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utilits.downloadCookies(req);

        Map<String, Object> model = new HashMap<>();


        PersonDAO personDAO = new PersonDAO();
        OpinionDAO opinionDAO = new OpinionDAO();
        List<Opinion> opinions =  opinionDAO.getLikesByPerson(userLogin);
        List<Person> persons = new ArrayList<>();
        for (Opinion op: opinions) {
            Person person = personDAO.get(op.getWhom());
            persons.add(person);
        }
        model.put("items", persons);
        Writer out = resp.getWriter();

        Utilits.freeMarkerRun(model,"people-list.html", out);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
