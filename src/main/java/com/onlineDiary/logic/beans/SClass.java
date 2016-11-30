package com.onlineDiary.logic.beans;

public class SClass {
    private int classId;
    private int studyYear;
    private String letter;

    public SClass() {
    }

    public SClass(int classId, int studyYear, String letter) {
        this.classId = classId;
        this.studyYear = studyYear;
        this.letter = letter;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}