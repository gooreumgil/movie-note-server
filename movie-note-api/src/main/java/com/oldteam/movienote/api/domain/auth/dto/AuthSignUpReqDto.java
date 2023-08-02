package com.oldteam.movienote.api.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthSignUpReqDto {

    private String name;
    private String email;
    private String password;

}
