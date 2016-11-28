package com.onlineDiary.logic;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ManagementSystem {
    private static Connection con;
    private static ManagementSystem instance;
    private static DataSource dataSource;

    public static synchronized ManagementSystem getInstance() {
        if (instance == null) {
            try {
                instance = new ManagementSystem();
                Context ctx = new InitialContext();
                dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/Diary");
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


    public boolean checkLogin(String login) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("SELECT login FROM users WHERE login = ?");
        stmt.setString(1, login);
        ResultSet rs = stmt.executeQuery();
        if (rs.next())
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

    public List<Mark> getMarks(int studId, int subjId) throws SQLException {
        List<Mark> marks = new ArrayList<>();
        Statement stmt = con.createStatement();

        ResultSet rs_marks = null;
        PreparedStatement pstmt = null;

        String subjectName = getSubjectName(subjId);

        pstmt = con.prepareStatement("SELECT  date, mark FROM marks\n" +
                "LEFT JOIN november on november.day=marks.date\n" +
                "where (id_teacher=1 and id_student=? and id_subject=?)\n");
        pstmt.setInt(1, studId);
        pstmt.setInt(2, subjId);
        rs_marks = pstmt.executeQuery();
        while (rs_marks.next()) {
            Date date = rs_marks.getDate(1);
            int mark = rs_marks.getInt(2);
            marks.add(new Mark(subjectName, date, mark));
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
        List<String> subjects = new ArrayList();
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


    /**
     * Сделал Тёма
     */
    public List<Student> getStudentsByClass(int classId) {
        ArrayList<Student> students = new ArrayList<>();
        ResultSet resultSet = null;
        try (PreparedStatement ps = con.prepareStatement("SELECT surname, name, patronymic, id FROM students" +
                " WHERE class_id = ?")) {
            ps.setInt(1, classId);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String surname = resultSet.getString(1);
                String name = resultSet.getString(2);
                String patronymic = resultSet.getString(3);
                int id = resultSet.getInt(4);
                students.add(new Student(name, surname, patronymic, classId, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
        }
        return students;
    }

    public List<Subject> getSubjects(int yearId) {
        ArrayList<Subject> subjects = new ArrayList<>();
        ResultSet resultSet = null;
        try (PreparedStatement ps = con.prepareStatement("SELECT subjects.name, subjects.id FROM subjects_in_classes" +
                " INNER JOIN subjects ON subject_id = subjects.id WHERE year_id = ?")) {
            ps.setInt(1, yearId);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String subjectName = resultSet.getString(1);
                int subjId = resultSet.getInt(2);
                subjects.add(new Subject(subjectName, subjId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
        }
        return subjects;
    }

    public List<SClass> getClasses() {
        ArrayList<SClass> classes = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement("SELECT id, year_id, letter FROM classes");
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int year = resultSet.getInt(2);
                String letter = resultSet.getString(3);
                classes.add(new SClass(id, year, letter));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public void addMark(int studId, int subjId, Date date, int mark) throws SQLException {
        Statement stmt = con.createStatement();

        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO marks " +
                    "(id_teacher, id_subject, id_student, date, mark) VALUE (1,1,1,'2016-11-24',5)");
            ps.setInt(1, studId);
            ps.setInt(2, subjId);
            ps.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
