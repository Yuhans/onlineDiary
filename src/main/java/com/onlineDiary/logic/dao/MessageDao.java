package com.onlineDiary.logic.dao;

import com.onlineDiary.logic.beans.Mark;
import com.onlineDiary.logic.beans.Message;
import com.onlineDiary.logic.executor.Executor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    private Executor executor;
    public MessageDao(Connection connection) {
        executor = new Executor(connection);
    }

    public List<Message> getMessages(String login, String receiver){
        String query = "SELECT text, date, receiver, sender FROM messages where (sender='"+login
                +"' and receiver='"+receiver+"') or (sender='"+
                receiver+"' and receiver='"+
                login + "') ;";
        return executor.execQuery(query, resultSet -> {
            List<Message> messages = new ArrayList<>();
            while (resultSet.next()) {
                String text = resultSet.getString(1);
                Timestamp date = resultSet.getTimestamp(2);
                String rec= resultSet.getString(3);
                String send= resultSet.getString(4);

                messages.add(new Message(send, rec, text, date));
            }
            return messages;
        });
    }
}
