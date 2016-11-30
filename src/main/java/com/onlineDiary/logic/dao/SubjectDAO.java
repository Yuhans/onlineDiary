package com.onlineDiary.logic.dao;

import com.onlineDiary.logic.beans.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private Connection connection;

    public SubjectDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Subject> getSubjectByYear(int year) {
        List<Subject> subjects = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT subjects.name, subjects.id FROM subjects_in_classes" +
                " INNER JOIN subjects ON subject_id = subjects.id WHERE year_id = ?")) {
            ps.setInt(1, year);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String subjectName = resultSet.getString(1);
                int subjId = resultSet.getInt(2);
                subjects.add(new Subject(subjectName, subjId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    public String getSubjectNameById(int id) {
        String subjectName = "";
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT name FROM subjects WHERE id=?")) {
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                subjectName = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjectName;
    }
}
