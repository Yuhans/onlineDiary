package com.onlineDiary.web.forms;


import com.onlineDiary.logic.SClass;
import com.onlineDiary.logic.Student;
import com.onlineDiary.logic.Subject;

import java.util.List;

public class MainFrameForm {

    private int classId;
    private List<SClass> sClasses;
    private List<Student> students;
    private List<Subject> subjects;

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    private List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<SClass> getsClasses() {
        return sClasses;
    }

    public void setsClasses(List<SClass> sClasses) {
        this.sClasses = sClasses;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
}
