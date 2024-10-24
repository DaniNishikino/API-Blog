package com.descomplica.frameblog.request;

public class AuthRequest {

    final String username;
    final String password;

    public AuthRequest(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
