package com.onlineDiary.logic.dao;

import com.onlineDiary.logic.Roles;
import com.onlineDiary.logic.beans.Student;
import com.onlineDiary.logic.beans.User;
import com.onlineDiary.logic.executor.Executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Executor executor;

    private static final int TEACHER = 0;
    private static final int STUDENT = 1;

    public UserDAO(Connection connection) {
        executor = new Executor(connection);
    }

    public User getUser(String login) {
        String query = "SELECT login, password, role FROM users WHERE login = " + "\"" + login + "\"";
        return executor.execQuery(query, resultSet -> {
            resultSet.next();
            int role = resultSet.getInt(3);
            Roles thisUserRole;
            if (role == TEACHER) {
                thisUserRole = Roles.TEACHER;
            } else {
                thisUserRole = Roles.STUDENT;
            }
            return new User(resultSet.getString(1), resultSet.getString(2), thisUserRole);
        });
    }

    public void insertUser(User user) {
        int userRoleInDB;
        if (user.getRole() == Roles.TEACHER) {
            userRoleInDB = TEACHER;
        } else {
            userRoleInDB = STUDENT;
        }
        String query = "INSERT INTO users (login, password, role) " +
                "VALUES (" + "\"" + user.getLogin() + "\",\"" + user.getPassword() + "\",\"" + userRoleInDB + "\")";
        executor.execUpdate(query);
    }

    public boolean isLoginExist(String login) {
        String query = "SELECT login FROM users WHERE login = " + "\"" + login + "\"";
        return executor.execQuery(query, ResultSet::next);
    }

    public List<User> getUsersWithoutName(String name){
        String query = "SELECT login FROM users WHere login!='"+name +"';";
        return executor.execQuery(query, resultSet -> {
            List<User> users = new ArrayList<>();
            String pass="";
            while (resultSet.next()) {
                String login = resultSet.getString(1);
                users.add(new User(login, pass, Roles.STUDENT));
            }
            return users;
        });
    }
}
