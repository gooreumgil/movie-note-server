package com.oldteam.movienote.api.domain.auth.dto;

import com.oldteam.movienote.core.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthTokenResDto {

    private Long id;
    private String nickname;
    private String email;
    private String imageUrl;
    private String accessToken;

    public static AuthTokenResDto of(Member member, String decryptedEmail, String accessToken) {
        AuthTokenResDto authTokenResDto = new AuthTokenResDto();
        authTokenResDto.id = member.getId();
        authTokenResDto.nickname = member.getNickname();
        authTokenResDto.email = decryptedEmail;
        authTokenResDto.accessToken = accessToken;
        return authTokenResDto;
    }

    public static AuthTokenResDto of(Member member, String decryptedEmail) {
        AuthTokenResDto authTokenResDto = new AuthTokenResDto();
        authTokenResDto.id = member.getId();
        authTokenResDto.nickname = member.getNickname();
        authTokenResDto.email = decryptedEmail;
        return authTokenResDto;
    }

}
