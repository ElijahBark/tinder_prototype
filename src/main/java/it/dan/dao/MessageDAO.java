package it.dan.dao;

import it.dan.entities.Message;
import it.dan.entities.Opinion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    private Connection connection;

    public MessageDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Message> getConversation(String login, String userLogin) {
        List<Message> messages = new ArrayList<>();

        String sql = "SELECT * FROM message_barkov WHERE (who=? AND whom =?) OR (who=? AND whom = ?)";

        try (
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, login);
            statement.setString(2, userLogin);
            statement.setString(3, userLogin);
            statement.setString(4, login);
            ResultSet rSet = statement.executeQuery();
            while (rSet.next()) {
                Message message = new Message();

                message.setText(rSet.getString("message_text"));
                message.setWho(rSet.getString("who"));
                message.setWhom(rSet.getString("whom"));
                message.setDate(rSet.getLong("message_date"));


                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }


    public void save(Message message) {
        String sql = "INSERT INTO message_barkov(who, whom, message_text, message_date) VALUES(?,?,?,?)";

        try (
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, message.getWho());
            statement.setString(2, message.getWhom());
            statement.setString(3, message.getText());
            statement.setLong(4, message.getDate());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
