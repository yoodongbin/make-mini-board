package com.example.util;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    private static final String LOGIN_MEMBER_ID = "LOGIN_MEMBER_ID";

    private SessionUtil() {
    }

    public static String getLoginMemberId(HttpSession session) {
        return (String) session.getAttribute(LOGIN_MEMBER_ID);
    }

    public static void setLoginMemberId(HttpSession session, String userId) {
        session.setAttribute(LOGIN_MEMBER_ID, userId);
    }
}
