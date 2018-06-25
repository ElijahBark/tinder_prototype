package it.dan;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import it.dan.AppRunner;
import it.dan.dao.OpinionDAO;
import it.dan.dao.PersonDAO;
import it.dan.entities.Opinion;
import it.dan.entities.Person;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import static it.dan.AppRunner.persons;
import static it.dan.AppRunner.userLogin;

public class Utilits {
    public static List<Person> downloadPersonsForUser(String login) {
        OpinionDAO opinionDAO = new OpinionDAO();
        PersonDAO personDAO = new PersonDAO();
        AppRunner.persons = personDAO.getOppositeSexPersonList(personDAO.get(login));
        List<Opinion> opinions =  opinionDAO.getWatchedByPerson(login);
        Set<String> logins = new HashSet<>();
        for (Opinion opinion: opinions) {
            logins.add(opinion.getWhom());
        }
        List<Person> newPersons = new ArrayList<>();
        for (Person person: AppRunner.persons) {
            if (!logins.contains(person.getLogin())) {
                newPersons.add(person);
            }
        }
        return newPersons;
    }

    public static void freeMarkerRun(Map<String, Object> model, String pageName, Writer out) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setDirectoryForTemplateLoading(new File("resources"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);

        Template template = cfg.getTemplate(pageName);
        try {
            template.process(model, out);

        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    public static void downloadCookies(HttpServletRequest req) {
        if (persons.size() == 0 || userLogin == null) {
            Cookie[] cookies = req.getCookies();
            for (Cookie ck : cookies) {
                if (ck.getName().equals("userid")) {
                    userLogin = ck.getValue();
                }
            }
            persons = Utilits.downloadPersonsForUser(userLogin);
        }
    }
}


