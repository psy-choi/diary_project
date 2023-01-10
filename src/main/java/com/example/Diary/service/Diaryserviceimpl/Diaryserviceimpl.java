package com.example.Diary.service.Diaryserviceimpl;

import com.example.Diary.Data.Entity.Join_Entity;
import com.example.Diary.Data.dao.JoinDAO;
import com.example.Diary.DiaryData.Entity.Diary_Entity;
import com.example.Diary.DiaryData.dao.DiaryDAO;
import com.example.Diary.DiaryData.dto.DiaryDTO;
import com.example.Diary.service.Diaryservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class Diaryserviceimpl implements Diaryservice {

    private final DiaryDAO diaryDAO;
    private final JoinDAO DB_member;

    @Autowired
    public Diaryserviceimpl (DiaryDAO diaryDAO, JoinDAO DB_member){
        this.diaryDAO = diaryDAO;
        this.DB_member = DB_member;
    }


    @Override
    public DiaryDTO getDiary(String User, String Date) {
        Join_Entity get_member;
        get_member = DB_member.get_Join_data(User);
        Diary_Entity get_Diary;
        get_Diary = diaryDAO.get_Diary_data(User, Date);
        DiaryDTO get_Diary_DTO = new DiaryDTO(get_member.getID(), get_Diary.getDate(), get_Diary.getDiary());
        return get_Diary_DTO;
    }

    @Override
    public DiaryDTO saveDiary(DiaryDTO Diary) {
        Diary_Entity save_diary = new Diary_Entity();
        save_diary.setUser(Diary.getUser());
        save_diary.setDate(Diary.getDate());
        save_diary.setDiary(Diary.getDiary());
        save_diary.setCreatedAt(LocalDateTime.now());
        save_diary.setUpdatedAt(LocalDateTime.now());

        diaryDAO.insert_Diary_data(save_diary);

        return Diary;

    }

    @Override
    public DiaryDTO changedDiary(String User, String Date, String Diary) throws Exception {
        Diary_Entity change_Diary = diaryDAO.update_Diary_data(User, Date, Diary);

        DiaryDTO changed_Diary = new DiaryDTO(change_Diary.getUser(), change_Diary.getDate(), change_Diary.getDiary());

        return changed_Diary;
    }

    @Override
    public void deleteDiary(String User, String Date) throws Exception {
        diaryDAO.delete_Join_data(User, Date);
    }
}
