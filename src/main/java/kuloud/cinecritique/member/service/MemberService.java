package kuloud.cinecritique.member.service;

import kuloud.cinecritique.common.exception.CustomException;
import kuloud.cinecritique.common.exception.ErrorCode;
import kuloud.cinecritique.member.dto.MemberData;
import kuloud.cinecritique.member.dto.MemberDto;
import kuloud.cinecritique.member.dto.MemberPostDto;
import kuloud.cinecritique.member.dto.MyPageDto;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberData signIn(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_LOGIN);
        }
        return new MemberData(member);
    }

    @Transactional
    public void signUp(MemberPostDto memberPostDto) {
        checkNicknameIsDuplicated(memberPostDto.getNickname());
        checkEmailIsDuplicated(memberPostDto.getEmail());
        memberRepository.save(memberPostDto.toEntityWithEncoder(passwordEncoder));
    }

    public MemberDto getMemberInformation(String nickname) {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        return new MemberDto(member);
    }

    public void checkNicknameIsDuplicated(String nickname) {
        if (memberRepository.findByNickname(nickname).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);
        }
    }

    public void checkEmailIsDuplicated(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    @Transactional
    public void updateMemberInfo(MemberPostDto memberPostDto, Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        Member updateMember = memberPostDto.toEntityWithEncoder(passwordEncoder);

        if (!member.getNickname().equals(memberPostDto.getNickname())) {
            checkNicknameIsDuplicated(updateMember.getNickname());
            member.changeNickName(updateMember.getNickname());
        }
        if (!member.getEmail().equals(memberPostDto.getEmail())) {
            checkEmailIsDuplicated(updateMember.getEmail());
            member.changeEmail(updateMember.getEmail());
        }
        member.changePassword(updateMember.getPassword());
    }

    public List<MyPageDto> getMemberList() {
        return memberRepository.findAll().stream().map(MyPageDto::new).toList();
    }

    @Transactional
    public void deleteMember(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        memberRepository.delete(member);
    }

    public MyPageDto getMyPageInfo(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        return new MyPageDto(member);
    }

}
