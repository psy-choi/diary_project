package com.example.Diary.Security.Security_Data.dao;


import com.example.Diary.Security.Security_Data.Entity.Security_Entity;
import com.example.Diary.Security.Security_Data.Security_Repository.SecurityRepository;
import com.example.Diary.Security.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class daoimpl {

    private final SecurityRepository securityRepository;


    @Autowired
    public daoimpl(SecurityRepository securityRepository){
        this.securityRepository = securityRepository;
    }

    // login 할때 저장되는 refresh 토큰
    public void login(Token token){
        Security_Entity security_entity = Security_Entity.builder()
                .keyEmail(token.getKey())
                .refreshToken(token.getRefreshToken()).build();
        String loginUserId = security_entity.getKeyEmail();
        if (securityRepository.existsByKeyEmail(loginUserId)){
            securityRepository.deleteByKeyEmail(loginUserId);
        }
        securityRepository.save(security_entity);
    }
    // refresh 토큰의 subject를 넣어놓고, 나중에 되찾는다.
    public Token getRefreshToken(String refreshToken){
        Security_Entity security_entity = securityRepository.findByRefreshToken(refreshToken);
        Token token = Token.builder().refreshToken(security_entity.getRefreshToken()).key(security_entity.getKeyEmail()).build();
        return token;
    }

}
