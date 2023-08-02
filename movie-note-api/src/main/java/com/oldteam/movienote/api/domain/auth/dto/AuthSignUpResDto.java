package com.oldteam.movienote.api.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthSignUpResDto {

    private Long id;
    private String nickname;
    private String imageUrl;
    private String accessToken;

}
