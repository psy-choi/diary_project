package com.example.Diary.Security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;


@Service
public class SecurityService {

    private static final String SECRET_KEY = "asdfghjklasdfghjklasdfghjklasdfghjklasdfghjklasdfghjklasdfghjklasdfghjklasdfghjklasdfghjklasdfghjklasdfghjkl";

    // 로그인 서비스 함께 할 때에 가져오기
    public String createToken(String subject, long expTime){
        if (expTime <= 0){
            throw new RuntimeException("만료시간이 0보다 짧습니다.");
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 새롭게 만드는 token 값

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);

        Key signingkey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder() // 위에서 subject 값에 값을 넣어줬으니까 대개 아이디를 만드는 것에 사용
                .setSubject(subject)
                .signWith(signingkey, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + expTime)) //현재시간 + 만료시간
                .compact();
    }

    // 토큰을 검증하는 로직에서 주로 사용한다. boolean 등으로 진행하게 된다.
    public String getSubject(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token) //token을 통해 해당 값이 풀어짐
                .getBody();


        return claims.getSubject();
    }
}
