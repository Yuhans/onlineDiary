package com.onlineDiary.logic.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class User implements Comparable{

    private String login;
    private String password;
    private int role;
    public User() {

    }

    public User (String login, String password, int role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(ResultSet rs) throws SQLException {
        setLogin(rs.getString(1));
        setPassword(rs.getString(2));
        setRole(rs.getInt(3));
    }

    public static boolean isLoginCorrect(String login) {
        Pattern p = Pattern.compile("^[a-z0-9_-]{4,16}$", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(login);
        return m.matches();
    }

    public static boolean isPasswordCorrect(String password) {
        Pattern p = Pattern.compile("^[a-z0-9_-]{6,16}$", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(password);
        return m.matches();
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
