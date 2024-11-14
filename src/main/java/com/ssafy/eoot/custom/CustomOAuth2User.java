package com.ssafy.eoot.custom;

import com.ssafy.eoot.dump.dto.TmemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final TmemberDTO tmemberDTO;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add((GrantedAuthority) () -> tmemberDTO.getRole());

        return collection;
    }

    @Override
    public String getName() {
        return tmemberDTO.getName();
    }

    public String getMemberName(){
        return tmemberDTO.getMemberName();
    }
}
