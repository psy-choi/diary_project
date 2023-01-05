package com.example.Diary.DiaryData.dto;

import com.example.Diary.DiaryData.Entity.Diary_Entity;

public class DiaryDTO {

    private String Date;

    private String User;

    private String Diary;

    public DiaryDTO(String User, String Date, String Diary){
        this.Date = Date;
        this.User = User;
        this.Diary = Diary;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getDiary() {
        return Diary;
    }

    public void setDiary(String diary) {
        Diary = diary;
    }
}
