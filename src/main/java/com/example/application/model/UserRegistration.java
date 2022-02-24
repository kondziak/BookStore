package com.example.application.model;

public class UserRegistration {

    private String name;
    private String last_name;
    private String password;
    private String email;

    public UserRegistration() {
    }

    public UserRegistration(String name, String last_name, String password, String email) {
        this.name = name;
        this.last_name = last_name;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
