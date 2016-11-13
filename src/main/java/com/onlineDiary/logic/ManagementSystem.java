package com.onlineDiary.logic;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Рамиль on 13.11.2016.
 */
public class ManagementSystem {
    private static Connection con;
    private static ManagementSystem instance;
    private static DataSource dataSource;

    public static synchronized ManagementSystem getInstance() {
        if (instance == null) {
            try {
                instance = new ManagementSystem();
                Context ctx = new InitialContext();
                instance.dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/Diary");
                con = dataSource.getConnection();
            } catch (NamingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public User getUserByLogin(String login) throws SQLException {
        User user = null;
        PreparedStatement stmt = con.prepareStatement("SELECT login, password, role FROM users WHERE login = ?");
        stmt.setString(1, login);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            user = new User(rs);
        }
        rs.close();
        stmt.close();
        return user;
    }

    public List getClasses() throws SQLException {
        List classes = new ArrayList();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT class_id, study_year, letter FROM classes");
        while (rs.next()) {
            SClass cl = new SClass();
            cl.setClassId(rs.getInt(1));
            cl.setStudyYear(rs.getInt(2));
            cl.setLetter(rs.getString(3));
            classes.add(cl);
        }
        rs.close();
        stmt.close();
        return classes;
    }

    public boolean checkLogin(String login) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("SELECT login FROM users WHERE login = ?");
        stmt.setString(1, login);
        ResultSet rs = stmt.executeQuery();
        if  (rs.next())
            return false;
        rs.close();
        stmt.close();
        return true;

    }

    public void addUser(User user) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("INSERT INTO users (login, password, role) VALUES (?,  ?,  ?)");
        stmt.setString(1, user.getLogin());
        stmt.setString(2, user.getPassword());
        stmt.setInt(3, user.getRole());
        stmt.execute();
    }
}
