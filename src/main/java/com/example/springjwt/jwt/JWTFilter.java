package com.example.springjwt.jwt;


import com.example.springjwt.domain.UserEntity;
import com.example.springjwt.dto.CustomUserDetails;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
public class JWTFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    public void doFilter(ServletRequest ServletRequest, ServletResponse ServletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) ServletRequest;
        HttpServletResponse response = (HttpServletResponse) ServletResponse;
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.split(" ")[1];

                //토큰 소멸 시간
                if (jwtUtil.isExpired(token)) {

                    log.info("Token is expired");
                    filterChain.doFilter(request, response);
                }

                String username = jwtUtil.getUsername(token);
                String role = jwtUtil.getRole(token);

                UserEntity userEntity = new UserEntity();
                userEntity.setUsername(username);
                userEntity.setPassword("temppassword");
                userEntity.setRole(role);

                CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

                Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }


            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("JWT Filter Error", e);
        }

    }
}
