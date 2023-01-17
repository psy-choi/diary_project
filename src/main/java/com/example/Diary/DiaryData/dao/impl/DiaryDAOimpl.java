package com.example.Diary.DiaryData.dao.impl;

import com.example.Diary.DiaryData.Entity.Diary_Entity;
import com.example.Diary.DiaryData.Repository.Diary_Repository;
import com.example.Diary.DiaryData.dao.DiaryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;


@Component
public class DiaryDAOimpl implements DiaryDAO {

    private final Diary_Repository diary_repository;

    @Autowired
    public DiaryDAOimpl(Diary_Repository diary_repository) {this.diary_repository = diary_repository;}

    @Override
    public Diary_Entity get_Diary_data(String User, String Date) {
        Long user = Long.parseLong(User);
        Diary_Entity get_Diary = diary_repository.findByUserID_NumberAndDate(user, Date);
        return get_Diary;
    }

    @Override
    public Diary_Entity insert_Diary_data(Diary_Entity Diary) {
        Diary_Entity save_diary = diary_repository.save(Diary);
        return save_diary;
    }

    @Override
    public Diary_Entity update_Diary_data(String User, String Date, String Diary) throws Exception {
        Diary_Entity updated_Diary = get_Diary_data(User, Date);
        Optional<Diary_Entity> present_data = diary_repository.findById(updated_Diary.getNumbers());
        Diary_Entity updated_Diary_done;
        if(present_data.isPresent()){
            updated_Diary.setDiary(Diary);
            updated_Diary.setUpdatedAt(LocalDateTime.now());

            updated_Diary_done = diary_repository.save(updated_Diary);
        } else {
            throw new Exception();
        }
        return updated_Diary_done;
    }

    @Override
    public void delete_Join_data(String User, String Date) throws Exception {
        Diary_Entity deleted_Diary = get_Diary_data(User, Date);
        Optional<Diary_Entity> present_data = diary_repository.findById(deleted_Diary.getNumbers());

        if (present_data.isPresent()){
            diary_repository.delete(deleted_Diary);
        } else {
            throw new Exception();
        }

    }
}
