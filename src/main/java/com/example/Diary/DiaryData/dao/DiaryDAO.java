package com.example.Diary.DiaryData.dao;

import com.example.Diary.Data.Entity.Join_Entity;
import com.example.Diary.DiaryData.Entity.Diary_Entity;

public interface DiaryDAO {
    Diary_Entity get_Diary_data(String User, String Date);
    Diary_Entity insert_Diary_data(Diary_Entity Diary);

    Diary_Entity update_Diary_data(String User, String Date, String Diary) throws Exception;

    void delete_Join_data(String User, String Date) throws Exception;

}
