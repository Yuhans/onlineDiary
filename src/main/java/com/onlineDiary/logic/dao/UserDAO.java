package com.onlineDiary.logic.dao;

import com.onlineDiary.logic.beans.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public User getUser(String login) {
        User user = null;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT login, password, role FROM users WHERE login = ?")) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String password = rs.getString(2);
                int role = rs.getInt(3);
                user = new User(login, password, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void insertUser(User user) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO users (login, password, role) VALUES (?,  ?,  ?)")) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getRole());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isLoginExist(String login) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT login FROM users WHERE login = ?")) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.wasNull())
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
