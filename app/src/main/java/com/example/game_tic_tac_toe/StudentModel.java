package com.example.game_tic_tac_toe;

import java.util.Random;

public class StudentModel {
    private int id;
    private String name;
    private String email;
    private String password;

    // Constructor with default values
    public StudentModel() {
        this.id = getAutoId();
        this.name = "";
        this.email = "";
        this.password="";
    }

    // Constructor with parameters
    public StudentModel(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password=password;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // Static method for generating random ID
    public static int getAutoId() {
        Random random = new Random();
        return random.nextInt(100);
    }
}
