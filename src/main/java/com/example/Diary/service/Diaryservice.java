package com.example.Diary.service;

import com.example.Diary.DiaryData.Repository.dto.DiaryDTO;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
public interface Diaryservice {
    DiaryDTO getDiary(String User, String Date);

    DiaryDTO saveDiary(DiaryDTO Diary);

    DiaryDTO changedDiary(String User, String Date, String Diary);

    void deleteDiary(String User, String Date);
}
