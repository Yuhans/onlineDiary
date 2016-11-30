package com.onlineDiary.logic.dao;

import com.onlineDiary.logic.beans.SClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SClassDAO {
    private Connection connection;

    public SClassDAO(Connection connection) {
        this.connection = connection;
    }

    public List<SClass> getAllClasses() {
        List<SClass> classes = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT id, year_id, letter FROM classes");
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
}
