package it.dan.dao;

import it.dan.entities.Opinion;
import it.dan.entities.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    public void save(Person person) {
        String sql = "INSERT INTO person_barkov(login, name, photo_url, sex, password) VALUES(?,?,?,?,?)";

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, person.getLogin());
            statement.setString(2, person.getName());
            statement.setString(3, person.getPhotoUrl());
            statement.setBoolean(4, person.getSex());
            statement.setString(5,person.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> getOppositeSexPersonList(Person user) {
        List<Person> persons = new ArrayList<>();

        String sql = "SELECT * FROM person_barkov WHERE sex= ?";

        try (
                Connection connection = ConnectionToDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);

        ) {
            statement.setBoolean(1, !user.getSex());
            ResultSet rSet = statement.executeQuery();
            while (rSet.next()) {
                Person person = new Person();

                person.setName(rSet.getString("name"));
                person.setPhotoUrl(rSet.getString("photo_url"));
                person.setLogin(rSet.getString("login"));
                person.setPassword(rSet.getString("password"));
                person.setSex(!user.getSex());


                persons.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }


    public Person get(Object login) {

        Person person = new Person();

        String sql = "SELECT * FROM person_barkov WHERE login=?";

        try (
                Connection connection = ConnectionToDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, String.valueOf(login));
            ResultSet rSet = statement.executeQuery();
            while (rSet.next()) {
                person.setLogin(rSet.getString("login"));
                person.setName(rSet.getString("name"));
                person.setPhotoUrl(rSet.getString("photo_url"));
                person.setSex(rSet.getBoolean("sex"));
                person.setPassword(rSet.getString("password"));

                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
