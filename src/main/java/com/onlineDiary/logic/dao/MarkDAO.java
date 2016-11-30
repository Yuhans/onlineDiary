package com.onlineDiary.logic.dao;

import com.onlineDiary.logic.beans.Mark;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarkDAO {
    private Connection connection;

    public MarkDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Mark> getMarksBySubject(int studentId, int subjectId) {
        List<Mark> marks = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT date, mark, subjects.name FROM marks\n" +
                "LEFT JOIN november ON november.day=marks.date\n" +
                "INNER JOIN subjects ON subjects.id=marks.id_subject\n" +
                "WHERE (id_teacher=1 AND id_student=? AND id_subject=?)\n")) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, subjectId);
            ResultSet rs_marks = pstmt.executeQuery();
            while (rs_marks.next()) {
                Date date = rs_marks.getDate(1);
                int mark = rs_marks.getInt(2);
                String subjectName = rs_marks.getString(3);
                marks.add(new Mark(subjectName, date, mark));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marks;
    }

    public void addMark(int studId, int subjId, Date date, int mark) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO marks" +
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
