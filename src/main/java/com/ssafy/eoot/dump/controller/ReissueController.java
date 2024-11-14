package com.ssafy.eoot.dump.controller;

import com.ssafy.eoot.dump.service.RedisService;
import com.ssafy.eoot.jwt.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
public class ReissueController {

    private final JWTUtil jwtUtil;
    private final RedisService redisService;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        // 쿠키에 존재하는 refreshToken을 가져오기
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if("refreshToken".equals(cookie.getName())) {
                refreshToken = cookie.getValue();
            }
        }

        // 검증
        // refreshToken이 없는 경우
        if(refreshToken == null) {
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        // 유효기간 확인
        try {
            if (jwtUtil.isExpired(refreshToken)){
                return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
            }
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인
        String category = jwtUtil.getCategory(refreshToken);

        if(!category.equals("refresh")) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        // 새로운 Token을 만들기 위해 준비
        String memberName = jwtUtil.getMemberName(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        // Redis내에 존재하는 refreshToken인지 확인
        String redisRefreshToken = redisService.getValues(memberName);
        if(redisService.checkExistsValue(redisRefreshToken)) {
            return new ResponseEntity<>("no exists in redis refresh token", HttpStatus.BAD_REQUEST);
        }

        // 새로운 JWT Token 생성
        String newAccessToken = jwtUtil.createJwt("accessToken", memberName, role, 600000L);
        String newRefreshToken = jwtUtil.createJwt("refreshToken", memberName, role, 86400000L);

        // update refreshToken to Redis
        redisService.setValues(memberName, newRefreshToken, Duration.ofMillis(86400000L));

        // 응답
        response.setHeader("accessToken", "Bearer " + newAccessToken);
        response.addCookie(createCookie("refreshToken", newRefreshToken));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);     // 쿠키가 살아있을 시간
        /*cookie.setSecure();*/         // https에서만 동작할것인지 (로컬은 http 환경이라 안먹음)
        /*cookie.setPath("/");*/        // 쿠키가 전역에서 동작
        cookie.setHttpOnly(true);       // http에서만 쿠키가 동작할 수 있도록 (js와 같은곳에서 가져갈 수 없도록)

        return cookie;
    }
}
