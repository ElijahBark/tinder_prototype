package it.dan.utilits;

import it.dan.AppRunner;
import it.dan.dao.OpinionDAO;
import it.dan.dao.PersonDAO;
import it.dan.entities.Opinion;
import it.dan.entities.Person;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


