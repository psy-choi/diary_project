package com.example.Diary.Data.dao.impl;


import com.example.Diary.Data.Entity.Join_Entity;
import com.example.Diary.Data.Repository.Join_Repository;
import com.example.Diary.Data.dao.JoinDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class JoinDAOimpl implements JoinDAO {
    private final Join_Repository join_repository;



    @Autowired
    public JoinDAOimpl(Join_Repository join_repository){
        this.join_repository = join_repository;
    }


    @Override
    public Join_Entity get_Join_data(String ID){
        Join_Entity get_join = join_repository.findByID(ID);
        return get_join;
    }

    @Override
    public Join_Entity get_Join_data_number(Long ID){
        Join_Entity get_join = join_repository.findBynumber(ID);
        return get_join;
    }

    @Override
    public Join_Entity insert_Join_data(Join_Entity join) {
        Join_Entity save_join = join_repository.save(join);
        return save_join;
    }

    @Override
    public Join_Entity update_Join_data(Long number, String ID, String Password) throws Exception {
        Optional<Join_Entity> present_Date = join_repository.findById(number); //NULL 값을 방지
        Join_Entity updated_join_Done;
        if (present_Date.isPresent()){
            Join_Entity updated_join = join_repository.getById(number);
            updated_join.setID(ID);
            updated_join.setPassword(Password);
            updated_join.setUpdatedAt(LocalDateTime.now());

            updated_join_Done = join_repository.save(updated_join);
        } else {
            throw new Exception();
        }
        return updated_join_Done;
    }

    @Override
    public void delete_Join_data(Long number) throws Exception {
        Optional<Join_Entity> present_Date = join_repository.findById(number);

        if (present_Date.isPresent()){
            Join_Entity updated_join = join_repository.getById(number);
            join_repository.delete(updated_join);
        } else {
            throw new Exception();
        }
    }
}
