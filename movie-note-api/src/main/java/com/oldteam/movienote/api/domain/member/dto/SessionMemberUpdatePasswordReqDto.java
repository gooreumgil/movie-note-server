package com.oldteam.movienote.api.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionMemberUpdatePasswordReqDto {

    private String password;
    private String newPassword;

}
