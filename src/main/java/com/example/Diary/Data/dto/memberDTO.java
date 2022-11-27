package com.example.Diary.Data.dto;

public class memberDTO {


    private String ID;
    private String Password;

    public memberDTO(String ID, String password){
        this.ID = ID;
        this.Password = password;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
}
