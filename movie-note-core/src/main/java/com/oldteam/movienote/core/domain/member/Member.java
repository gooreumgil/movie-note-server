package com.oldteam.movienote.core.domain.member;

import com.oldteam.movienote.core.audit.AuditingDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "members")
public class Member extends AuditingDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nickname;
    private String email;
    private String imageUrl;
    private String password;

    public static Member create(String name, String email, String password) {
        Member member = new Member();
        member.name = name;
        member.email = email;
        member.password = password;
        return member;
    }

}
