package com.onlineDiary.web;

import com.onlineDiary.logic.ManagementSystem;
import com.onlineDiary.logic.Mark;
import com.onlineDiary.logic.SClass;
import com.onlineDiary.logic.Student;
import com.onlineDiary.web.forms.MainFrameForm;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import static java.io.FileDescriptor.out;


public class MainFrameServlet extends HttpServlet {
    private final ManagementSystem dao = ManagementSystem.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            request.setAttribute("classes", dao.getClasses());
            MainFrameForm form = new MainFrameForm();
            int classId = 0;
            if (request.getParameter("stClass") != null) {
                classId = Integer.parseInt(request.getParameter("stClass"));
            }
            List<SClass> classes = dao.getClasses();
            SClass schoolClass = new SClass();
            schoolClass.setClassId(classId);
            if (classId == -1) {
                Iterator i = classes.iterator();
                schoolClass = (SClass) i.next();
            }
            List<Student> students = dao.getStudentsByClass(classId);
            form.setClassId(schoolClass.getClassId());
            form.setsClasses(classes);
            form.setStudents(students);
            request.setAttribute("form", form);
            if (request.getParameter("stClass") != null) {
                request.setAttribute("students", dao.getStudentsByClass(classId));
                schoolClass = dao.getClasses().get(classId - 1);
                request.setAttribute("subjects", dao.getSubjects(schoolClass.getStudyYear()));
            }
            if (request.getParameter("Log out") != null) {
                response.sendRedirect("/auth");
                return;
            }

            if ((request.getParameter("studentId") != null) & (request.getParameter("subjId") != null)) {
                int studId = Integer.parseInt(request.getParameter("studentId"));
                int subjId = Integer.parseInt(request.getParameter("subjId"));
                //try {
                    form.setMarks(dao.getMarks(studId, subjId));
                    request.setAttribute("marks", form.getMarks());
                //} catch (SQLException sql_e) {
                  //  throw new IOException(sql_e.getMessage());
               // }
            }
            request.getRequestDispatcher("MainFrame.jsp").forward(request, response);
        } else {
            response.sendRedirect("/auth");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            request.setAttribute("classes", dao.getClasses());
            request.getRequestDispatcher("MainFrame.jsp").forward(request, response);
        } else {
            response.sendRedirect("/auth");
        }
    }
}