package com.onlineDiary.web;

import com.onlineDiary.logic.ManagementSystem;
import com.onlineDiary.logic.account.AccountService;
import com.onlineDiary.web.forms.ChatForm;
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
    private ChatForm form = new ChatForm();
    String login = "bellka";

    private static final Logger LOGGER = Logger.getLogger(AddMarkServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAuthorized(request)) {
            setUsers(request, login);
            request.getRequestDispatcher("Chat.jsp").forward(request, response);
        } else {
            response.sendRedirect("/auth");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAuthorized(request)) {
            getReceiver(request);
            request.getRequestDispatcher("Chat.jsp").forward(request, response);

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

    private void setUsers(HttpServletRequest request, String receiver) {
        request.setAttribute("messages", dao.getMessages(login, receiver));
        form.setUsers(dao.getUsersWithoutName(login));
        request.setAttribute("form", form);
        //request.setAttribute("selectedUser", receiver);
        request.setAttribute("users", dao.getUsersWithoutName(login));
    }

    private void getReceiver(HttpServletRequest request) {
        if (request.getParameter("receiver") != null) {
            String rec = request.getParameter("receiver");
            setUsers(request, rec);
            //String selectuser = rec;
            request.setAttribute("selectedUser", rec);
        }

    }
}
