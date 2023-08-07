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

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;
    private String email;
    private String imageUrl;
    private String password;

    public static Member create(String name, String nickname, String email, String password, MemberRole memberRole) {
        Member member = new Member();
        member.name = name;
        member.nickname = nickname;
        member.email = email;
        member.memberRole = memberRole;
        member.password = password;
        return member;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
