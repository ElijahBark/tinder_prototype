package it.dan.dao;

import it.dan.entities.Opinion;
import it.dan.entities.Opinion.PersonLike;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OpinionDAO implements InterfaceDAO<Opinion> {


    @Override
    public void save(Opinion opinion) {
        String sql = "INSERT INTO opinion(who, whom, person_like) VALUES(?,?,?)";

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, opinion.getWho());
            statement.setString(2, opinion.getWhom());
            statement.setString(3, String.valueOf(opinion.getLike()));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Opinion> getLikesByPerson(String login) {
        List<Opinion> opinions = new ArrayList<>();

        String sql = "SELECT * FROM opinion WHERE who='" + login + "' AND person_like ='Like'";

        try (
                Connection connection = ConnectionToDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rSet = statement.executeQuery();
        ) {
            while (rSet.next()) {
                Opinion opinion = new Opinion();

                opinion.setWho(rSet.getString("who"));
                opinion.setWhom(rSet.getString("whom"));
                opinion.setOpinionLike();


                opinions.add(opinion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return opinions;
    }

    public List<Opinion> getWatchedByPerson(String login) {
        List<Opinion> opinions = new ArrayList<>();

        String sql = "SELECT * FROM opinion WHERE who='" + login + "'";

        try (
                Connection connection = ConnectionToDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rSet = statement.executeQuery();
        ) {
            while (rSet.next()) {
                Opinion opinion = new Opinion();

                opinion.setWho(rSet.getString("who"));
                opinion.setWhom(rSet.getString("whom"));
                if (rSet.getString("person_like").equals("Like")) {
                    opinion.setOpinionLike();
                } else {
                    opinion.setOpinionDisLike();
                }


                opinions.add(opinion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return opinions;
    }


    @Override
    public Opinion get(Object pk) {
        return null;
    }

    @Override
    public void update(Opinion obj) {

    }

    @Override
    public void delete(Object pk) {

    }
}
