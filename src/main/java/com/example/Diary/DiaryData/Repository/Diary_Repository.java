package com.example.Diary.DiaryData.Repository;



import com.example.Diary.DiaryData.Entity.Diary_Entity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Diary_Repository extends JpaRepository<Diary_Entity, Long> {

    Diary_Entity findByUserAndDate(String User, String Date);
}
