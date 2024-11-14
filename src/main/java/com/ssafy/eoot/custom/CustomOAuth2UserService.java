package com.ssafy.eoot.custom;

import com.ssafy.eoot.config.oauth2.GoogleResponse;
import com.ssafy.eoot.config.oauth2.NaverResponse;
import com.ssafy.eoot.config.oauth2.OAuth2Response;
import com.ssafy.eoot.dump.dto.TmemberDTO;
import com.ssafy.eoot.dump.entity.TMemberEntity;
import com.ssafy.eoot.dump.repository.MemberRepsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepsitory memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 부모 클래스의 메서드를 사용하여 객체를 생성함.
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 제공자
        String registration = userRequest.getClientRegistration().getRegistrationId();

        // 제공자별로 객체를 구현하여 OAuth2Response 타입으로 반환할거다.
        // 이 다음 섹션에서 구현할거다.
        OAuth2Response oAuth2Response = null;

        // 제공자별 분기 처리
        if("naver".equals(registration)) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if("google".equals(registration)) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        // 사용자명을 제공자_회원아이디 형식으로만들어 저장할거다. 이 값은 redis에서도 key로 쓰일 예정
        String memberName = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();

        // 넘어온 회원정보가 이미 우리의 테이블에 존재하는지 확인
        TMemberEntity existData = memberRepository.findByMemberName(memberName);

        // 존재하지 않는다면 회원정보를 저장하고 CustomOAuth2User 반환
        if(existData == null) {
            TMemberEntity memberEntity = new TMemberEntity();
            memberEntity.setMemberName(memberName);
            memberEntity.setName(oAuth2Response.getName());
            memberEntity.setEmail(oAuth2Response.getEmail());
            memberEntity.setProfileImage(oAuth2Response.getProfileImage());
            memberEntity.setRole("ROLE_MEMBER");

            memberRepository.save(memberEntity);

            TmemberDTO memberDTO = new TmemberDTO();
            memberDTO.setMemberName(memberName);
            memberDTO.setName(oAuth2Response.getName());
            memberDTO.setProfileImage(oAuth2Response.getProfileImage());
            memberDTO.setRole("ROLE_MEMBER");

            return new CustomOAuth2User(memberDTO);
        } else {  // 회원정보가 존재한다면 조회된 데이터로 반환한다.
            existData.setEmail(oAuth2Response.getEmail());
            existData.setName(oAuth2Response.getName());
            existData.setProfileImage(oAuth2Response.getProfileImage());

            memberRepository.save(existData);

            TmemberDTO memberDTO = new TmemberDTO();
            memberDTO.setMemberName(memberName);
            memberDTO.setName(existData.getName());
            memberDTO.setProfileImage(existData.getProfileImage());
            memberDTO.setRole("ROLE_USER");

            return new CustomOAuth2User(memberDTO);
        }
    }
}
