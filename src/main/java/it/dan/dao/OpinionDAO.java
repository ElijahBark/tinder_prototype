package it.dan.dao;

import it.dan.entities.Opinion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OpinionDAO {

    public void save(Opinion opinion) {
        String sql = "INSERT INTO opinion_barkov(who, whom, person_like) VALUES(?,?,?)";

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, opinion.getWho());
            statement.setString(2, opinion.getWhom());
            statement.setBoolean(3, opinion.getLike());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Opinion> getLikesByPerson(String login) {
        List<Opinion> opinions = new ArrayList<>();

        String sql = "SELECT * FROM opinion_barkov WHERE who=? AND person_like =?";

        try (
                Connection connection = ConnectionToDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1,login);
            statement.setBoolean(2, true);
            ResultSet rSet = statement.executeQuery();
            while (rSet.next()) {
                Opinion opinion = new Opinion();
                opinion.setWho(rSet.getString("who"));
                opinion.setWhom(rSet.getString("whom"));
                opinion.setOpinionLike(true);


                opinions.add(opinion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return opinions;
    }

    public List<Opinion> getWatchedByPerson(String login) {
        List<Opinion> opinions = new ArrayList<>();

        String sql = "SELECT * FROM opinion_barkov WHERE who=?";

        try (
                Connection connection = ConnectionToDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1,login);
            ResultSet rSet = statement.executeQuery();
            while (rSet.next()) {
                Opinion opinion = new Opinion();

                opinion.setWho(rSet.getString("who"));
                opinion.setWhom(rSet.getString("whom"));
                opinion.setOpinionLike(rSet.getBoolean("person_like"));
                opinions.add(opinion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return opinions;
    }
}
