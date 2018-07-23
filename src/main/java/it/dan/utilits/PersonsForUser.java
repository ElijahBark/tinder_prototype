package it.dan.utilits;

import it.dan.AppRunner;
import it.dan.dao.OpinionDAO;
import it.dan.dao.PersonDAO;
import it.dan.entities.Opinion;
import it.dan.entities.Person;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonsForUser {
    public static List<Person> download(String login, Connection connection) {
        OpinionDAO opinionDAO = new OpinionDAO(connection);
        PersonDAO personDAO = new PersonDAO(connection);
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

}
