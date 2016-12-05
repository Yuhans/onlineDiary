package com.onlineDiary.web.forms;


import com.onlineDiary.logic.beans.User;

import java.util.List;

public class ChatForm {
    String receiver;
    List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }



    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
