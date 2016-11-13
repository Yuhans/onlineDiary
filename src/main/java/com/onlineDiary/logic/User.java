package com.onlineDiary.logic;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Рамиль on 12.11.2016.
 */
public class User implements Comparable{

    private String login;
    private String password;
    private int role;
    public User() {

    }

    public User(ResultSet rs) throws SQLException {

        setLogin(rs.getString(1));
        setPassword(rs.getString(2));
        setRole(rs.getInt(3));
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() { return role; }

    public void setRole(int role) {
        this.role = role;
    }

    public String toString() {
        return login + " " + password + " " + role;
    }

    public int compareTo(Object obj) {
        return this.toString().compareTo(obj.toString());
    }
}
