package com.example.Diary.service;

import com.example.Diary.DiaryData.dto.DiaryDTO;
import org.springframework.stereotype.Service;

@Service
public interface Diaryservice {
    DiaryDTO getDiary(String User, String Date);

    DiaryDTO saveDiary(DiaryDTO Diary);

    DiaryDTO changedDiary(String User, String Date, String Diary) throws Exception;

    void deleteDiary(String User, String Date) throws Exception;
}
