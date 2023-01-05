package com.example.Diary.Data.dao;

import com.example.Diary.Data.Entity.Join_Entity;

public interface JoinDAO {
    Join_Entity get_Join_data(String ID);
    Join_Entity insert_Join_data(Join_Entity join);

    Join_Entity update_Join_data(Long number, String ID, String Password) throws Exception;

    void delete_Join_data(Long number) throws Exception;

}
