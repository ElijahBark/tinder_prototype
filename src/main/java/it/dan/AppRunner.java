package it.dan;

import it.dan.entities.Person;
import it.dan.servlets.*;
import it.dan.utilits.FreeMarkerObject;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.*;


public class AppRunner {

    public static String userLogin;

    public static List<Person> persons = new ArrayList<>();
    public static int coef;

    public static void main(String[] args) {
//        persons.add( new Person("Alex","Alex", "https://i.ytimg.com/vi/hYvkSHYh_WQ/hqdefault.jpg"));
//        persons.add( new Person("Lily","Lily", "http://cdn2.stylecraze.com/wp-content/uploads/2013/10/2.-Anushka-Sharma_1.jpg"));
//        persons.add( new Person("Rose","Rose", "http://images-hdwallpapers.com/wp-content/uploads/2017/05/Sweet-Girl-image-in-The-Worl-600x375.jpg"));
//        persons.add( new Person("Kimmy","Kimmy", "https://cdn.pixabay.com/photo/2016/11/16/10/27/girl-1828538__340.jpg"));
//        persons.add( new Person("Anastasia","Anastasia", "https://pbs.twimg.com/profile_images/739247958340698114/fVKY9fOv.jpg"));

//        String userLogin = "Ilia";
//        OpinionDAO opinionDAO = new OpinionDAO();
//        PersonDAO personDAO = new PersonDAO();
//        persons = personDAO.getOppositeSexPersonList(personDAO.get(userLogin));
//        List<Opinion> opinions =  opinionDAO.getWatchedByPerson(userLogin);
//        Set<String> logins = new HashSet<>();
//        for (Opinion opinion: opinions) {
//            logins.add(opinion.getWhom());
//        }
//        List<Person> newPersons = new ArrayList<>();
//        for (Person person: persons) {
//            if (!logins.contains(person.getLogin())) {
//                newPersons.add(person);
//            }
//        }
//        persons = newPersons;


        Server serv = new Server(8008);
        ServletContextHandler context = new ServletContextHandler();

        FreeMarkerObject freeMarkerObject = new FreeMarkerObject();

        ServletHolder userHolder = new ServletHolder(new UserServlet(freeMarkerObject));
        ServletHolder testHolder = new ServletHolder(new StaticServlet());
        ServletHolder likedHolder = new ServletHolder(new LikedServlet(freeMarkerObject));
        ServletHolder chatHolder = new ServletHolder(new ChatServlet(freeMarkerObject));
        ServletHolder loginHolder = new ServletHolder(new LoginServlet());

        Filter loginFilter = new LoginFilter();
        FilterHolder loginFIlterHolder = new FilterHolder(loginFilter);


        context.addServlet(userHolder, "/users");
        context.addServlet(testHolder, "/static/*");
        context.addServlet(likedHolder, "/liked");
        context.addServlet(chatHolder, "/chat/*");
        context.addServlet(loginHolder, "/login");

        context.addFilter(loginFIlterHolder, "/users", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));
        context.addFilter(loginFIlterHolder, "/liked", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));
        context.addFilter(loginFIlterHolder, "/chat/*", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));


        serv.setHandler(context);
        try {
            serv.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            serv.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
