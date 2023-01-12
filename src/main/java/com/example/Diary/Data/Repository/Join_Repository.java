package com.example.Diary.Data.Repository;

import com.example.Diary.Data.Entity.Join_Entity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Join_Repository extends JpaRepository<Join_Entity, Long> {
    Join_Entity findByID(String ID);

    Join_Entity findFirst1Bynumber(Long ID);
}

