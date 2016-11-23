package com.onlineDiary.logic;

import java.sql.Date;

/**
 * Created by Tatyana on 23.11.2016.
 */
public class Mark {
    String subject;
    Date date;
    int mark;


    public Mark(String subject, Date date, int mark) {
        this.subject = subject;
        this.date = date;
        this.mark = mark;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
