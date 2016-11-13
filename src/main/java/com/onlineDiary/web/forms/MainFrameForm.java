package com.onlineDiary.web.forms;

import java.util.Collection;

/**
 * Created by Рамиль on 13.11.2016.
 */
public class MainFrameForm {
    private int classId;
    private Collection sClasses;

    public Collection getsClasses() {
        return sClasses;
    }

    public void setsClasses(Collection sClasses) {
        this.sClasses = sClasses;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
}
