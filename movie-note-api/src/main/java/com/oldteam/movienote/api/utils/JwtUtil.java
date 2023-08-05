package com.oldteam.movienote.api.utils;

import com.oldteam.movienote.api.domain.member.mapper.MemberTokenMapper;
import com.oldteam.movienote.common.utils.AES256Util;
import com.oldteam.movienote.core.domain.member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private final Key key;

    private static Long PLUS_MILLS = (1000 * 60 * 60 * 24) * 5L;

    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(Member member) throws Exception {


        JwtBuilder builder = Jwts.builder()
                .claim("id", member.getId())
                .claim("email", AES256Util.decrypt(member.getEmail()))
                .claim("name", member.getName())
                .claim("role", member.getMemberRole().name());

        return builder
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expireTime())
                .compact();

    }

    public MemberTokenMapper getMemberTokenMapper(String token) {
        Claims claims = getClaims(token);
        if (claims == null) {
            throw new RuntimeException("잘못된 토큰입니다");
        }
        MemberTokenMapper memberTokenMapper = new MemberTokenMapper(claims);
        return memberTokenMapper;
    }


    private Date expireTime() {
        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + PLUS_MILLS);
        return expireTime;
    }

    public boolean checkTokenExpired(Date expiration) {
        Date date = new Date();
        long currentTime = date.getTime(); // 현재시간
        long expirationTime = expiration.getTime(); // 만료시간

        if (currentTime > expirationTime) { // 현재시간이 만료시간 이후라면 세션 종료 에러
            throw new RuntimeException("만료된 토큰입니다");
        }

        long leftTime = expirationTime - currentTime; // 남은시간: 만료시간 - 현재시간
        long refreshTime = (long) (1000 * 60 * 60 * 24); // 리프레시타임: 1일

        // 리프레시 타임이 남은시간 보다 크면 true
        // 리프레시 타임이 남은시간 보다 작으면 false
        return leftTime < refreshTime;
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

}
