package com.ssafy.eoot.member.service;

import com.ssafy.eoot.exception.member.MemberNotFoundException;
import com.ssafy.eoot.exception.member.PasswordMismatchException;
import com.ssafy.eoot.member.dto.LoginForm;
import com.ssafy.eoot.member.entity.Member;
import com.ssafy.eoot.member.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceV1Impl implements MemberServiceV1{
    private final MemberRepository memberRepository;
    private BCryptPasswordEncoder encoder;

    @Override
    public void login(LoginForm loginForm, String lastLoginType) {
        Member member = memberRepository.findById(loginForm.getId())
                .orElseThrow(MemberNotFoundException::new);
        if (!encoder.matches(loginForm.getPass(), member.getPassword())) {
            throw new PasswordMismatchException();
        }
        member.setLastLoginType(lastLoginType);
        memberRepository.save(member);
    }

    @Override
    public Member getMemberById(String memberId) {
        return memberRepository.getReferenceById(memberId);
    }
}
