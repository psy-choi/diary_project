package com.example.Diary.service;

import com.example.Diary.Data.dto.*;

public interface memberservice {
    memberresponsDTO getMember();

    memberresponsDTO saveMember(memberDTO member);

    memberresponsDTO changedMember(Long number, String ID, String Password) throws Exception;

    void deleteMember(Long number) throws Exception;
}
