package com.example.Diary.service;

import com.example.Diary.Data.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface memberservice {

    memberresponsDTO getMember(String ID);

    memberresponsDTO saveMember(memberDTO member);

    memberresponsDTO changedMember(Long number, String ID, String Password);

    void deleteMember(Long number);
}
