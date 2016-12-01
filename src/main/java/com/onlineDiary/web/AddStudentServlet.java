package com.onlineDiary.web;

import com.onlineDiary.logic.ManagementSystem;
import com.onlineDiary.logic.beans.SClass;
import com.onlineDiary.logic.beans.Student;
import com.onlineDiary.web.forms.MainFrameForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Tatyana on 01.12.2016.
 */
public class AddStudentServlet extends HttpServlet {
    private ManagementSystem dao = new ManagementSystem();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("classes", dao.getClasses());
        MainFrameForm form = new MainFrameForm();
        int classId = 0;
        if (request.getParameter("stClass") != null) {
            classId = Integer.parseInt(request.getParameter("stClass"));
        }
        if (request.getParameter("OkB") != null) {
            String tmp = request.getParameter("name");
            String tmp2 = request.getParameter("surname");
            String tmp3 = request.getParameter("patronymic");
            if (tmp == null || tmp2 == null || tmp3 == null || classId==0) {
                request.setAttribute("submitDone", "no");
            } else {
                String name = request.getParameter("name");
                String surname = request.getParameter("surname");
                String patronymic = request.getParameter("patronymic");
                Student s = new Student(name, surname, patronymic, classId, 0);
                dao.addStudent(s);
                request.setAttribute("submitDone", "yes");
            }
            request.getRequestDispatcher("AddStudent.jsp").forward(request, response);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("classes", dao.getClasses());
        request.getRequestDispatcher("AddStudent.jsp").forward(request, response);
    }
}
