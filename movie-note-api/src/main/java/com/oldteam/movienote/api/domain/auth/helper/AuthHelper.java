package com.oldteam.movienote.api.domain.auth.helper;

import com.oldteam.movienote.api.domain.auth.dto.AuthSignUpResDto;
import com.oldteam.movienote.core.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthHelper {

    public AuthSignUpResDto convertToAuthSignUpResDto(Member member, String accessToken) {
        return new AuthSignUpResDto(member.getId(), member.getNickname(), member.getImageUrl(), accessToken);
    }

}
