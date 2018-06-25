package it.dan.servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import it.dan.dao.OpinionDAO;
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
import java.util.HashMap;
import java.util.Map;

import static it.dan.AppRunner.coef;
import static it.dan.AppRunner.persons;
import static it.dan.AppRunner.userLogin;

public class UserServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utilits.downloadCookies(req);

        if (coef >= persons.size() || persons.size() == 0) {
            resp.sendRedirect("/liked");
        } else {
            Person current = persons.get(coef);

            Map<String, Object> model = new HashMap<>();
            model.put("name", current.getName());
            model.put("url", current.getPhotoUrl());

            Writer out = resp.getWriter();

            Utilits.freeMarkerRun(model,"like-page.html", out);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String likeResponse = req.getParameter("buttonlike");
        OpinionDAO opinionDAO = new OpinionDAO();
        Person current = persons.get(coef);

        Opinion opinion = new Opinion(userLogin, current.getName());
        if (likeResponse.equals("like")) {
            opinion.setOpinionLike();
        } else if (likeResponse.equals("dislike")) {
            opinion.setOpinionDisLike();

        }
        opinionDAO.save(opinion);
        if (coef < persons.size() - 1) {
            coef++;
            doGet(req, resp);
        } else {
            coef++;
            resp.sendRedirect("liked");
        }
    }
}
