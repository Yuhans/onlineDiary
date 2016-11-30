package com.onlineDiary.logic.beans;

public class Student {
    private String name;
    private String surname;
    private String patronymic;
    private int classId;
    private int studentId;

    public Student() {
    }

    public Student(String name, String surname, String patronymic, int classId, int studentId) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.classId = classId;
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
