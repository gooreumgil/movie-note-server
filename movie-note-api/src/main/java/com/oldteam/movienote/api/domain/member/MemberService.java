package com.oldteam.movienote.api.domain.member;

import com.oldteam.movienote.api.domain.auth.dto.AuthSignUpReqDto;
import com.oldteam.movienote.api.domain.uploadfile.UploadFileService;
import com.oldteam.movienote.clients.awsresource.service.AwsS3Service;
import com.oldteam.movienote.common.exception.HttpException;
import com.oldteam.movienote.common.exception.HttpExceptionCode;
import com.oldteam.movienote.common.utils.AES256Util;
import com.oldteam.movienote.core.domain.member.Member;
import com.oldteam.movienote.core.domain.member.MemberRole;
import com.oldteam.movienote.core.domain.member.repository.MemberRepository;
import com.oldteam.movienote.core.domain.uploadfile.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final UploadFileService uploadFileService;
    private final PasswordEncoder passwordEncoder;

    private static final String MEMBER_DIRECTORY = "members";
    private static final String MEMBER_PROFILE_IMAGE_DIRECTORY = "profile-image";

    @Transactional
    public Member save(AuthSignUpReqDto saveReqDto) {

        String name = saveReqDto.getName();
        String nickname = saveReqDto.getNickname();
        String email = saveReqDto.getEmail();
        String encryptEmail;

        try {
            encryptEmail = AES256Util.encrypt(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Optional<Member> optionalMember = memberRepository.findByEmail(encryptEmail);
        if (optionalMember.isPresent()) {
            throw new RuntimeException("존재하는 이메일입니다.");
        }

        String password = saveReqDto.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        Member member = Member.create(name, nickname, encryptEmail, encodedPassword, MemberRole.MEMBER);

        Member savedMember = memberRepository.save(member);

        Long uploadFileId = saveReqDto.getUploadFileId();
        if (uploadFileId != null) {
            Optional<UploadFile> optionalUploadFile = uploadFileService.findById(uploadFileId);
            optionalUploadFile.ifPresent(savedMember::setUploadFile);
        }
//        try {
//            String imageUrl = awsS3Service.saveFile(profileImage, MEMBER_DIRECTORY + "/" + savedMember.getId() + "/" + MEMBER_PROFILE_IMAGE_DIRECTORY);
//            savedMember.setImageUrl(imageUrl);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        return savedMember;

    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Member findByEmail(String email) {

        String encryptEmail;

        try {
            encryptEmail = AES256Util.encrypt(email);
        } catch (Exception e) {
            throw new HttpException(HttpStatus.BAD_REQUEST, HttpExceptionCode.EMAIL_ENCRYPT_FAIL, e);
        }

        return memberRepository.findByEmail(encryptEmail).orElseThrow(RuntimeException::new);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

}
