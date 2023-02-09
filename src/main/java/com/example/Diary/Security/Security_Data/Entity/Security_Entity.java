package com.example.Diary.Security.Security_Data.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@Getter
@Table(name = "refresh_Token")
@NoArgsConstructor
@AllArgsConstructor
public class Security_Entity {

    // 해당 아이디
    @Id
    @Column(name = "KEY_EMAIL", nullable = false)
    private String keyEmail;
    // 해당 암호화 된 refresh 토큰
    @Column(name = "REFRESH_TOKEN", nullable = false)
    private String refreshToken;
    // 해당 암호화된 subject


}
