package com.onlineDiary.logic.dao;


import com.onlineDiary.logic.beans.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection connection;

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Student> getStudents(int classId) {
        List<Student> students = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT surname, name, patronymic, id FROM students" +
                " WHERE class_id = ?")) {
            ps.setInt(1, classId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String surname = resultSet.getString(1);
                String name = resultSet.getString(2);
                String patronymic = resultSet.getString(3);
                int id = resultSet.getInt(4);
                students.add(new Student(name, surname, patronymic, classId, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public void addStudent(int classId, String name, String surname, String patronymic){
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO students " +
                "(surname, name, patronymic, class_id) VALUE (?,?,?,?)")) {
            ps.setString(1, surname);
            ps.setString(2, name);
            ps.setString(3, patronymic);
            ps.setInt(4, classId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
