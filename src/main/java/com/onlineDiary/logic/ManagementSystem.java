package com.onlineDiary.logic;

import com.onlineDiary.logic.beans.*;
import com.onlineDiary.logic.dao.UserDAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ManagementSystem {
    private static Connection con;
    private static ManagementSystem instance;
    private static DataSource dataSource;

    public ManagementSystem() {
        con = getConnection();
    }

    public static synchronized ManagementSystem getInstance() {
        if (instance == null) {
            try {
                instance = new ManagementSystem();
                Context ctx = new InitialContext();
                dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/Diary");
                con = dataSource.getConnection();
            } catch (NamingException | SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private Connection getConnection() {
        if (con == null) {
            try {
                Context ctx = new InitialContext();
                dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/Diary");
                con = dataSource.getConnection();
            } catch (NamingException | SQLException e) {
                e.printStackTrace();
            }
        }
        return con;
    }

    public User getUserByLogin(String login) {
        UserDAO userDAO = new UserDAO(con);
        return userDAO.getUser(login);
    }


    public boolean checkLogin(String login) {
        UserDAO userDAO = new UserDAO(con);
        return userDAO.isLoginExist(login);
    }

    public void addUser(User user) {
        UserDAO userDAO = new UserDAO(con);
        userDAO.insertUser(user);
    }

    public List<Mark> getMarks(int studId, int subjId) {
        List<Mark> marks = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement("SELECT  date, mark FROM marks\n" +
                "LEFT JOIN november ON november.day=marks.date\n" +
                "WHERE (id_teacher=1 AND id_student=? AND id_subject=?)\n")) {
            String subjectName = getSubjectName(subjId);
            pstmt.setInt(1, studId);
            pstmt.setInt(2, subjId);
            ResultSet rs_marks = pstmt.executeQuery();
            while (rs_marks.next()) {
                Date date = rs_marks.getDate(1);
                int mark = rs_marks.getInt(2);
                marks.add(new Mark(subjectName, date, mark));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public List getSubjects() {
        List<String> subjects = new ArrayList<>();
        ResultSet rs = null;
        try (Statement stmt = con.createStatement()) {
            rs = stmt.executeQuery("SELECT name FROM SUBJECTS");
            while (rs.next()) {
                subjects.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return subjects;
    }

    private String getSubjectName(int subjId) {
        String subjectName = "";
        //Statement stmt = con.createStatement();
        ResultSet rs_name = null;
        try (PreparedStatement pstmt = con.prepareStatement("SELECT name FROM SUBJECTS WHERE id=?")) {
            pstmt.setInt(1, subjId);
            rs_name = pstmt.executeQuery();
            if (rs_name.next()) {
                subjectName = rs_name.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs_name != null) {
                    rs_name.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return subjectName;
    }


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
                    e.printStackTrace();
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
                    e.printStackTrace();
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

    public void addMark(int studId, int subjId, Date date, int mark) {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO marks" +
                "(id_teacher, id_subject, id_student, date, mark) VALUE (1,?,?,?,?)")) {
            ps.setInt(1, subjId);
            ps.setInt(2, studId);
            ps.setDate(3, date);
            ps.setInt(4, mark);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
