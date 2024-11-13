package com.ssafy.eoot.member.controller;

import com.ssafy.eoot.member.dto.LoginForm;
import com.ssafy.eoot.member.dto.LoginSuccessDto;
import com.ssafy.eoot.member.dto.LogoutSuccessDto;
import com.ssafy.eoot.member.service.MemberServiceV1;
import com.ssafy.eoot.response.ResponseDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/member/")
public class MemberControllerV1 {
    private final MemberServiceV1 memberService;

    @PostMapping("/login/{lastLoginType}")
    public ResponseEntity<?> login(@PathVariable String lastLoginType, @RequestBody LoginForm loginForm) {
        memberService.login(loginForm, lastLoginType);
        return ResponseDto.success(
                LoginSuccessDto.builder()
                        .memberId(loginForm.getId())
                        .lastLoginType(lastLoginType)
                        .timestamp(Timestamp.from(Instant.now())).build()
                , 2000);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String memberId, HttpSession session) {
        session.invalidate();
        String lastLoginType = memberService.getMemberById(memberId).getLastLoginType();
        return ResponseDto.success(
                LogoutSuccessDto.builder()
                        .status(true)
                        .lastLoginType(lastLoginType)
                        .timestamp(Timestamp.from(Instant.now())).build()
                , 2000);
    }
}
