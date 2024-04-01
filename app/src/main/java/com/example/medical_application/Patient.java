package com.example.medical_application;

public class Patient {

    private String name,user,dp;



    public Patient(String name,  String user, String dp) {
        this.name = name;
        this.user = user;
        this.dp=dp;
    }

    public String getName() {
        return name;
    }
    public String getuser() {
        return user;
    }
    public String getdp() {
        return dp;
    }
}
