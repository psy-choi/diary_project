package com.example.Diary.Data.dto;

public class memberresponsDTO {
    private Long number;
    private String ID;
    private String Password;

    public memberresponsDTO(Long number, String ID, String password){
        this.number = number;
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

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
