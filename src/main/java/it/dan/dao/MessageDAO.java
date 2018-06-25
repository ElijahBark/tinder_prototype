package it.dan.dao;

import it.dan.entities.Message;
import it.dan.entities.Opinion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO implements InterfaceDAO<Message> {


    public List<Message> getConversation(Object login, Object userLogin) {
        List<Message> messages = new ArrayList<>();

        String sql = "SELECT * FROM message WHERE (who='" + login + "' AND whom ='"+userLogin+"') OR (who='" + userLogin + "' AND whom ='"+login+"')";

        try (
                Connection connection = ConnectionToDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rSet = statement.executeQuery();
        ) {
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


    @Override
    public void save(Message message) {
        String sql = "INSERT INTO message(who, whom, message_text, message_date) VALUES(?,?,?,?)";

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, message.getWho());
            statement.setString(2, message.getWhom());
            statement.setString(3, message.getText());
            statement.setLong(4,message.getDate());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(Message obj) {

    }

    @Override
    public Message get(Object pk) {
        return null;
    }

    @Override
    public void delete(Object pk) {

    }
}
