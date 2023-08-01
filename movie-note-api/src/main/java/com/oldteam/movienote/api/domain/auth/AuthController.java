package com.oldteam.movienote.api.domain.auth;

import com.oldteam.movienote.api.domain.auth.dto.AuthLoginReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {


    @PostMapping("/login")
    public void login(@RequestBody AuthLoginReqDto dto) {

    }

}
