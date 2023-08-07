package com.oldteam.movienote.api.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthSignUpReqDto {

    private String name;
    private String nickname;
    private MultipartFile profileImage;
    private String email;
    private String password;

}
