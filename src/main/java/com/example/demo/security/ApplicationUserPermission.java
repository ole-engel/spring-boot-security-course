package com.example.demo.security;

public enum ApplicationUserPermission {
    STUDENT_READ("STUDENT:READ"),
    STUDENT_WRITE("STUDENT:WRITE"),
    COURSES_READ("COURSES:READ"),
    COURSES_WRITE("COURSES:WRITE");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
