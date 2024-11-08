package com.ssafy.eoot.config.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Key key;

    // 활성화된 토큰을 저장하는 자료구조 - 맵
    private Map<String, String> activeTokens = new ConcurrentHashMap<>();
    // 무효화된 토큰을 저장하는 자료구조 - 셋
    private Set<String> invalidTokens = ConcurrentHashMap.newKeySet();

    @PostConstruct
    public void init(){
        byte[] keyBytes = secret.getBytes();
        if(keyBytes.length < 64){
            throw new IllegalArgumentException("경고: 비밀 키 길이는 64이상으로 할 것.");
        }
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 새로운 JWT 토큰 생성
    public String generateToken(String memberId){

    }

    // 토큰 생성
    public String doGenerateToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    //JWT 토큰을 생성
    public String getUidFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims != null ? claims.getSubject() : null;
    }

    //JWT 토큰에서 모든 클레임을 추출
    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            logger.error("토큰의 유효기간이 지나 만료되었습니다. 다시 로그인 해주세요");
            throw e;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("토큰이 유효하지 않습니다");
            throw e;
        }
    }


}
