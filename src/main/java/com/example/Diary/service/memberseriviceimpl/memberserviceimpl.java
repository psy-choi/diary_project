package com.example.Diary.service.memberseriviceimpl;

import com.example.Diary.Data.Entity.Join_Entity;
import com.example.Diary.Data.dao.JoinDAO;
import com.example.Diary.service.memberservice;
import com.example.Diary.Data.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class memberserviceimpl implements memberservice {

    private final JoinDAO DB_member;

    @Autowired
    public memberserviceimpl(JoinDAO DB_member){
        this.DB_member = DB_member;
    }

    @Override
    public memberresponsDTO getMember(String ID){
        Join_Entity get_member;
        get_member = DB_member.get_Join_data(ID);
        memberresponsDTO response = new memberresponsDTO(get_member.getNumber(), get_member.getID(), get_member.getPassword());
        return response;
    }


    @Override
    public memberresponsDTO saveMember(memberDTO member) {
        Join_Entity save_member = new Join_Entity();
        save_member.setID(member.getID());
        save_member.setPassword(member.getPassword());
        save_member.setCreatedAt(LocalDateTime.now());
        save_member.setUpdatedAt(LocalDateTime.now());

        Join_Entity saved_member = DB_member.insert_Join_data(save_member);

        memberresponsDTO response = new memberresponsDTO(saved_member.getNumber(), saved_member.getID(), saved_member.getPassword());

        return response;
    }

    @Override
    public memberresponsDTO changedMember(Long number, String ID, String Password) {
        Join_Entity change_member = DB_member.update_Join_data(number, ID, Password);

        memberresponsDTO response = new memberresponsDTO(change_member.getNumber(), change_member.getID(), change_member.getPassword());

        return response;
    }

    @Override
    public void deleteMember(Long number){
        DB_member.delete_Join_data(number);
    }
}
