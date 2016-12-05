package com.onlineDiary.web;

import com.onlineDiary.logic.ManagementSystem;
import com.onlineDiary.logic.account.AccountService;
import com.onlineDiary.web.forms.MainFrameForm;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.onlineDiary.logic.Roles.TEACHER;

public class ChatServlet extends HttpServlet {
    private ManagementSystem dao = new ManagementSystem();
    private AccountService accountService = AccountService.getInstance();

    private static final Logger LOGGER = Logger.getLogger(AddMarkServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAuthorized(request)) {
            String login="bellka";
            String receiver="asd";
            request.setAttribute("messages", dao.getMessages(login, receiver));

            request.getRequestDispatcher("Chat.jsp").forward(request, response);
//            if (isTeacher(request)) {
//
//                request.getRequestDispatcher("Chat.jsp").forward(request, response);
//            } else {
//                response.sendRedirect("/main");
//            }
        } else {
            response.sendRedirect("/auth");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAuthorized(request)) {


        } else {
            response.sendRedirect("/auth");
        }
    }

    private boolean isAuthorized(HttpServletRequest request) {
        return accountService.getUserBySessionId(request.getSession().getId()) != null;
    }

    private boolean isTeacher(HttpServletRequest request) {
        return accountService.getUserBySessionId(request.getSession().getId()).getRole() == TEACHER;
    }
}
