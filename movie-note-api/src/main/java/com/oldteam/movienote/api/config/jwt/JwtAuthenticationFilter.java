package com.oldteam.movienote.api.config.jwt;

import com.oldteam.movienote.api.domain.member.mapper.MemberTokenMapper;
import com.oldteam.movienote.api.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private static final String AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;

        Authentication authentication;

        try {
            authentication = getAuthentication((HttpServletRequest) request);
        } catch (ExpiredJwtException | MalformedJwtException e) {
            res.setStatus(401);
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            return;
        }

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);

    }

    private Authentication getAuthentication(HttpServletRequest request) {

        String token = request.getHeader(AUTHORIZATION);
        if (token == null || !token.contains(TOKEN_PREFIX)) {
            return null;
        }

        Claims claims = jwtUtil.getClaims(token.substring("Bearer ".length()));
        if (claims == null) {
            return null;
        }

        Set<GrantedAuthority> roles = new HashSet<>();
        String role = String.valueOf(claims.get("role"));
        roles.add(new SimpleGrantedAuthority("ROLE_" + role));

        MemberTokenMapper memberTokenMapper = new MemberTokenMapper(claims);

        return new UsernamePasswordAuthenticationToken(memberTokenMapper, null, roles);

    }


}
