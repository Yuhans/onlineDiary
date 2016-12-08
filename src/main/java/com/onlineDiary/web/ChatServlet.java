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

import static com.onlineDiary.logic.Roles.STUDENT;
import static com.onlineDiary.logic.Roles.TEACHER;

public class ChatServlet extends HttpServlet {
    private ManagementSystem dao = new ManagementSystem();
    private ChatForm form = new ChatForm();
    private String login;
    private String receiver;

    private static final Logger LOGGER = Logger.getLogger(AddMarkServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (RequestHandler.isAuthorized(request)) {
            RequestHandler.setRole(request);
            setUsers(request);
            login = RequestHandler.getLogin(request);
            request.setAttribute("sender", login);
            request.getRequestDispatcher("Chat.jsp").forward(request, response);
        } else {
            response.sendRedirect("/auth");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (RequestHandler.isAuthorized(request)) {
            RequestHandler.setRole(request);
            setChatForm(request, response);

        } else {
            response.sendRedirect("/auth");
        }
    }

    private void sendMessage(HttpServletRequest request) {
        if ((request.getParameter("messOk") != null)
                & (request.getParameter("newMessage") != null)) {
            String text = request.getParameter("newMessage");
            dao.addMessage(login, receiver, text);
            LOGGER.info("Message from " + login + " to " + receiver + " was sent!");
        }
    }

    private void setUsers(HttpServletRequest request) {
        request.setAttribute("messages", dao.getMessages(login, receiver));
        form.setUsers(dao.getUsersWithoutName(login));
        request.setAttribute("form", form);
        request.setAttribute("users", dao.getUsersWithoutName(login));
    }

    private void setReceiver(HttpServletRequest request) {
        if (request.getParameter("receiver") != null) {
            receiver = request.getParameter("receiver");
        }
        request.setAttribute("selectedUser", receiver);
        setUsers(request);

    }

    private void setChatForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sendMessage(request);
        //setUsers(request);
        setReceiver(request);

        request.getRequestDispatcher("Chat.jsp").forward(request, response);

    }

}
