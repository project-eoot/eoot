package com.ssafy.eoot.jwt;

import com.ssafy.eoot.custom.CustomOAuth2User;
import com.ssafy.eoot.dump.dto.TmemberDTO;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 요청 헤더에 있는 accessToken이라는 값을 가져오기.
        String accessToken = request.getHeader("accessToken");

        // 요청 헤더에 accessToken이 없는 경우
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Bearer 제거하기 <- OAuth2를 이용한다고 명시적으로 붙여주는 타입
        String originToken = accessToken.substring(7);

        // 유효한지 확인 후 Client로 상태 코드 응답
        try {
            if(jwtUtil.isExpired(originToken)){
                PrintWriter writer = response.getWriter();
                writer.println("access token expired");

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } catch (ExpiredJwtException e){
            PrintWriter writer = response.getWriter();
            writer.println("access token expired");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // accessToken인지 refreshToken인지 확인
        String category = jwtUtil.getCategory(originToken);

        // JWTFilter 는 요 청에 대해 accessToken만 취급하므로 accessToken인지 확인
        if(!"accessToken".equals(category)){
            PrintWriter writer = response.getWriter();
            writer.println("invalid access token");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 사용자 명과 권한을 accessToken에서 추출하기
        String memberName = jwtUtil.getMemberName(originToken);
        String role = jwtUtil.getRole(originToken);

        TmemberDTO memberDTO = new TmemberDTO();
        memberDTO.setMemberName(memberName);
        memberDTO.setRole(role);

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(memberDTO);

        Authentication authentication = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
