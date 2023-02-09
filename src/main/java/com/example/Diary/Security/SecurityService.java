package com.example.Diary.Security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Diary.Security.Security_Data.dao.daoimpl;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;




@Service
public class SecurityService {

    private static final String SECRET_KEY = "asdfghjklasdfghjklasdfghjklasdfghjklasdfghjklasdfghjklasdfghjklasdfghjklasdfghjklasdfghjklasdfghjklasdfghjkl";
    private final daoimpl daoimpl;

    @Autowired
    public SecurityService(daoimpl daoimpl){
        this.daoimpl = daoimpl;
    }


    // 로그인 서비스 함께 할 때에 가져오기
    public void login(Token token){
        daoimpl.login(token);
    }

    public Token createToken(String subject, long expTime, long refreshedTime){
        if (expTime <= 0){
            throw new RuntimeException("만료시간이 0보다 짧습니다.");
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 새롭게 만드는 token 값

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);

        Key signingkey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        String accessToken = Jwts.builder() // 위에서 subject 값에 값을 넣어줬으니까 대개 아이디를 만드는 것에 사용
                .setSubject(subject)
                .signWith(signingkey, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + expTime)) //현재시간 + 만료시간
                .compact();

        String refresh_subject = subject + subject ;
        String refreshToken = Jwts.builder()
                .setSubject(refresh_subject)
                .signWith(signingkey, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + refreshedTime))
                .compact();

        return Token.builder().accessToken(accessToken).refreshToken(refreshToken).key(subject).build();
    }

    public String recreationAccessToken(String subject, long expTime){
        if (expTime <= 0){
            throw new RuntimeException("만료시간이 0보다 짧습니다.");
        }
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 새롭게 만드는 token 값
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingkey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        String accessToken = Jwts.builder()
                .setSubject(subject)
                .signWith(signingkey, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + expTime)) //현재시간 + 만료시간
                .compact();
        return accessToken;
    }

    public String validateRefreshToken(String refreshToken){
        System.out.println("뀨");
        Token token = daoimpl.getRefreshToken(refreshToken);
        String subject = token.getKey() + token.getKey();
        String other_subject = getSubject(token.getRefreshToken());
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .build()
                    .parseClaimsJws(refreshToken);
            // 만료 시간도 지나고, check 해 보니까 refresh가 동일할 때
            if (!claims.getBody().getExpiration().before(new Date()) && subject.equals(other_subject)){
                return recreationAccessToken(claims.getBody().getSubject(), 1000*60*10);
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    // 토큰을 검증하는 로직에서 주로 사용한다. boolean 등으로 진행하게 된다.
    public String getSubject(String accesstoken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(accesstoken) //token을 통해 해당 값이 풀어짐
                .getBody();
        return claims.getSubject();
    }
}
