package com.onlineDiary.logic.beans;

import com.onlineDiary.logic.Roles;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class User implements Comparable{

    private String login;
    private String password;
    private Roles role;

    public User() {

    }

    public User (String login, String password, Roles role) {
        this.login = login;
        this.password = password;
        this.role = role;
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

    public Roles getRole() { return role; }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String toString() {
        return login + " " + password + " " + role;
    }

    public int compareTo(Object obj) {
        return this.toString().compareTo(obj.toString());
    }
}
