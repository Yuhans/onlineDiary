package com.onlineDiary.logic;

import com.onlineDiary.logic.beans.*;
import com.onlineDiary.logic.dao.SClassDAO;
import com.onlineDiary.logic.dao.StudentDAO;
import com.onlineDiary.logic.dao.SubjectDAO;
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
        return (new UserDAO(con).getUser(login));
    }


    public boolean checkLogin(String login) {
        return (new UserDAO(con).isLoginExist(login));
    }

    public void addUser(User user) {
        new UserDAO(con).insertUser(user);
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

    private String getSubjectName(int subjId) {
        return (new SubjectDAO(con).getSubjectNameById(subjId));
    }


    public List<Student> getStudentsByClass(int classId) {
        return (new StudentDAO(con).getStudents(classId));
    }

    public List<Subject> getSubjects(int yearId) {
        return (new SubjectDAO(con).getSubjectByYear(yearId));
    }

    public List<SClass> getClasses() {
        return (new SClassDAO(con).getAllClasses());
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
