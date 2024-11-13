package com.ssafy.eoot.member.repo;

import com.ssafy.eoot.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
