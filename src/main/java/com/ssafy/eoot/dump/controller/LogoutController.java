package com.ssafy.eoot.dump.controller;

import com.ssafy.eoot.dump.service.RedisService;
import com.ssafy.eoot.jwt.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LogoutController {

    private final JWTUtil jwtUtil;
    private final RedisService redisService;

    @PostMapping("/logout")
    public Long logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return -1L;
        }

        Optional<Cookie> refreshCookie = Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst();

        if(refreshCookie.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return -1L;
        }

        String refreshToken = refreshCookie.get().getValue();
        if(refreshToken == null || refreshToken.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return -1L;
        }

        String key = jwtUtil.getMemberName(refreshToken);

        if(redisService.getValues(key) == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return -1L;
        }

        redisService.deleteValues(key);

        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.setStatus(HttpServletResponse.SC_OK);
        response.addCookie(cookie);

        return 1L;
    }
}
