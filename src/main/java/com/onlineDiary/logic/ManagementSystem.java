package com.onlineDiary.logic;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ������ on 13.11.2016.
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

    public List getMarks(int studId, int subjId) throws SQLException {
        List marks = new ArrayList();
        Statement stmt = con.createStatement();

        ResultSet rs_marks = null;
        PreparedStatement pstmt = null;

        pstmt = con.prepareStatement("SELECT  mark FROM marks\n" +
                "RIGHT JOIN november on november.day=marks.date\n" +
                "where (id_teacher=1 and id_student=? and id_subject=?) \n" +
                "      or id_student IS NULL");
        pstmt.setInt(1, studId);
        pstmt.setInt(2, subjId);
        rs_marks = pstmt.executeQuery();
        while (rs_marks.next()) {
            marks.add(rs_marks.getInt(1));
        }

        rs_marks.close();
        stmt.close();
        pstmt.close();

        return marks;
    }

    public List getDates() throws SQLException {
        List dates = new ArrayList();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM NOVEMBER");
        while (rs.next()) {
            dates.add(rs.getDate(1));
        }
        rs.close();
        stmt.close();
        return dates;
    }

    public List getSubjects() throws SQLException {
        List subjects = new ArrayList();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT name FROM SUBJECTS");
        while (rs.next()) {
            subjects.add(rs.getString(1));
        }
        rs.close();
        stmt.close();
        return subjects;
    }

    public String getSubjectName(int subjId) throws SQLException {
        String subjectName = "";
        Statement stmt = con.createStatement();
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement("SELECT name FROM SUBJECTS where id=?");
        pstmt.setInt(1, subjId);
        ResultSet rs_name = pstmt.executeQuery();
        if (rs_name.next()) {
            subjectName = rs_name.getString(1);
        }
        pstmt.close();
        rs_name.close();
        stmt.close();
        return subjectName;
    }
}
