package com.onlineDiary.logic;

import com.onlineDiary.logic.beans.*;
import com.onlineDiary.logic.dao.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;


public class ManagementSystem {
    private Connection con;

    public ManagementSystem() {
        con = getConnection();
    }

    private Connection getConnection() {
        if (con == null) {
            try {
                Context ctx = new InitialContext();
                DataSource dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/Diary");
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

    public boolean checkLogin(String login) { return (new UserDAO(con).isLoginExist(login)); }

    public void addUser(User user) { new UserDAO(con).insertUser(user); }

    public List<Mark> getMarks(int studId, int subjId) {
        return (new MarkDAO(con).getMarksBySubject(studId, subjId));
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

    public void addMark(int studId, int subjId, Date date, int mark) { new MarkDAO(con).addMark(studId, subjId, date, mark); }

    public void addStudent(Student s){ new StudentDAO(con).addStudent(s); }

}
