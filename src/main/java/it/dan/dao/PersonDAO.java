package it.dan.dao;

import it.dan.entities.Opinion;
import it.dan.entities.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO implements InterfaceDAO<Person> {

    @Override
    public void save(Person person) {
        String sql = "INSERT INTO person(login, name, photo_url) VALUES(?,?,?,?)";

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, person.getLogin());
            statement.setString(2, person.getName());
            statement.setString(3, String.valueOf(person.getPhotoUrl()));
            statement.setBoolean(4, person.getSex());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> getOppositeSexPersonList(Person user) {
        List<Person> persons = new ArrayList<>();

        Boolean bool = !user.getSex();


        String sql = "SELECT * FROM person WHERE sex='" + bool + "'";

        try (
                Connection connection = ConnectionToDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rSet = statement.executeQuery();
        ) {
            while (rSet.next()) {
                Person person = new Person();

                person.setName(rSet.getString("name"));
                person.setPhotoUrl(rSet.getString("photo_url"));
                person.setLogin(rSet.getString("login"));


                persons.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    @Override
    public void update(Person person) {

    }

    @Override
    public Person get(Object login) {

        Person person = new Person();

        String sql = "SELECT * FROM person WHERE login='" + login + "'";

        try (
                Connection        connection  = ConnectionToDB.getConnection();
                PreparedStatement statement  = connection.prepareStatement(sql);
                ResultSet rSet = statement.executeQuery();
        )
        {
            while ( rSet.next() )
            {
                person.setLogin(rSet.getString("login"));
                person.setName(rSet.getString("name"));
                person.setPhotoUrl(rSet.getString("photo_url"));
                person.setSex(rSet.getBoolean("sex"));
                person.setPassword(rSet.getString("user_pass"));

                return person;
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Object pk) {

    }
}
