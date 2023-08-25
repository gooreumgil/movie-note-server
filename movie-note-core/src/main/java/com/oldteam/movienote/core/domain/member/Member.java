package com.oldteam.movienote.core.domain.member;

import com.oldteam.movienote.core.audit.AuditingDomain;
import com.oldteam.movienote.core.domain.movie.MovieReview;
import com.oldteam.movienote.core.domain.movie.MovieReviewLike;
import com.oldteam.movienote.core.domain.movie.MovieReviewLikeHistory;
import com.oldteam.movienote.core.domain.movie.MovieReviewReply;
import com.oldteam.movienote.core.domain.uploadfile.UploadFile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "upload_file_id")
    private UploadFile uploadFile;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;
    private String email;
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MovieReview> movieReviewList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MovieReviewLike> reviewLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MovieReviewLikeHistory> reviewLikeHistoryList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MovieReviewReply> replyList = new ArrayList<>();

    public static Member create(String name, String nickname, String email, String password, MemberRole memberRole) {
        Member member = new Member();
        member.name = name;
        member.nickname = nickname;
        member.email = email;
        member.memberRole = memberRole;
        member.password = password;
        return member;
    }

    public void setUploadFile(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    public void addMovieReview(MovieReview movieReview) {
        this.movieReviewList.add(movieReview);
        movieReview.setMember(this);
    }

    public void addReviewLike(MovieReviewLike movieReviewLike) {
        this.reviewLikeList.add(movieReviewLike);
        movieReviewLike.setMember(this);
    }

    public void addReviewLikeHistory(MovieReviewLikeHistory movieReviewLikeHistory) {
        this.reviewLikeHistoryList.add(movieReviewLikeHistory);
        movieReviewLikeHistory.setMember(this);
    }

    public void addReviewReply(MovieReviewReply movieReviewReply) {
        this.replyList.add(movieReviewReply);
        movieReviewReply.setMember(this);
    }
}
