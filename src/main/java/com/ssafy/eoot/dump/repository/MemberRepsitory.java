package com.ssafy.eoot.dump.repository;

import com.ssafy.eoot.dump.entity.TMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepsitory extends JpaRepository<TMemberEntity, Long> {
    TMemberEntity findByMemberName(String memberName);
}
