package com.onlineDiary.logic.beans;

public class Subject {
    private int subjId;
    private String subjName;

    public Subject() {
    }

    public Subject(String subjName, int subjId) {
        this.subjName = subjName;
        this.subjId = subjId;
    }

    public int getSubjId() {
        return subjId;
    }

    public void setSubjId(int subjId) {
        this.subjId = subjId;
    }

    public String getSubjName() {
        return subjName;
    }

    public void setSubjName(String subjName) {
        this.subjName = subjName;
    }
}
