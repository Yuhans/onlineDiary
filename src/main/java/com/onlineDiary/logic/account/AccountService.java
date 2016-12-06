package com.onlineDiary.logic.account;

import com.onlineDiary.logic.beans.User;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private Map<String, User> sessionIdToProfile = new HashMap<>();
    private static AccountService accountService;

    private AccountService() {
    }

    public static AccountService getInstance() {
        if (accountService == null) {
            accountService = new AccountService();
        }
        return accountService;
    }

    public User getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, User userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }

}
