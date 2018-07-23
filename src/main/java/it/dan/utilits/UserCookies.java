package it.dan.utilits;

import it.dan.AppRunner;
import it.dan.dao.OpinionDAO;
import it.dan.dao.PersonDAO;
import it.dan.entities.Opinion;
import it.dan.entities.Person;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static it.dan.AppRunner.persons;
import static it.dan.AppRunner.userLogin;

public class UserCookies {
    public static void download(HttpServletRequest req, Connection connection) {
        if (persons.size() == 0 || userLogin == null) {
            Cookie[] cookies = req.getCookies();
            for (Cookie ck : cookies) {
                if (ck.getName().equals("userid")) {
                    userLogin = ck.getValue();
                }
            }
            persons = PersonsForUser.download(userLogin, connection);
        }
    }
}


