package com.ssafy.eoot.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey secretKey;

    // application.yml에 있는 평문 secret key를 가져와 초기화하였다.
    // 여기서는 HS256으로 진행했다.
    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        // HS256 알고리즘을 사용하여 비밀키를 설정
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    }

    // 사용자 이름 가져오기
    public String getMemberName(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("memberName", String.class);
    }

    // 권한 추출
    public String getRole(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();


        return claims.get("role", String.class);
    }

    // token 유효 확인
    public Boolean isExpired(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Date expiration = claims.getExpiration();

        return expiration.before(new Date());
    }

    // accessToken인지 refreshToken인지 확인
    public String getCategory(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("category", String.class);
    }

    // JWT 발급
    public String createJwt(String category, String memberName, String role, Long expireMs) {
        return Jwts.builder()
                .claim("category", category)
                .claim("memberName", memberName)
                .claim("role", role)
                .setIssuedAt(new Date())  // 현재 시간으로 발행 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + expireMs))  // 만료 시간 설정
                .signWith(secretKey, SignatureAlgorithm.HS256)  // 서명 알고리즘 명시적으로 설정
                .compact();  // JWT 토큰 생성
    }
}
