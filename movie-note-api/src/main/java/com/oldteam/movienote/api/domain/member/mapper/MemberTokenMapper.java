package com.oldteam.movienote.api.domain.member.mapper;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberTokenMapper {

    private Long id;
    private String email;
    private String name;

    public MemberTokenMapper(Claims claims) {
        int userId = (Integer) claims.get("id");
        this.id = (long) userId;
        this.email = (String) claims.get("email");
        this.name = (String) claims.get("name");
    }
}
