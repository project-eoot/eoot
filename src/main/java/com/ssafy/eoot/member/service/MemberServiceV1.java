package com.ssafy.eoot.member.service;

import com.ssafy.eoot.member.dto.LoginForm;
import com.ssafy.eoot.member.entity.Member;

public interface MemberServiceV1 {
    void login(LoginForm loginForm, String lastLoginType);
    Member getMemberById(String memberId);
}
