package kuloud.cinecritique.member.service;

import kuloud.cinecritique.common.entity.JwtResponse;
import kuloud.cinecritique.common.exception.CustomException;
import kuloud.cinecritique.common.exception.ErrorCode;
import kuloud.cinecritique.common.security.JwtTokenProvider;
import kuloud.cinecritique.member.dto.MemberDto;
import kuloud.cinecritique.member.dto.MemberPostDto;
import kuloud.cinecritique.member.dto.MyPageDto;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public ResponseEntity<Void> createMemberAccountAndSave(MemberPostDto memberPostDto) {
        if (memberRepository.findByNickname(memberPostDto.getNickname()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);
        }
        memberRepository.save(memberPostDto.toEntityWithEncoder(passwordEncoder));
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<MemberDto> getMemberInformation(String nickname) {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        return ResponseEntity.ok(MemberDto.createWith(member));
    }

    public ResponseEntity<Void> checkNicknameIsDuplicated(String nickname) {
        if (memberRepository.findByNickname(nickname).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);
        }
        return ResponseEntity.noContent().build();
    }

    public void checkEmailIsExist(String email) {
        if (memberRepository.findByEmail(email).isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST_EMAIL);
        }
    }

    public ResponseEntity<List<MyPageDto>> getMemberList() {
        return ResponseEntity.ok(memberRepository.findAll().stream().map(MyPageDto::createWith).toList());
    }

    public ResponseEntity<MyPageDto> getMyPageInfo(String nickname) {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        return ResponseEntity.ok(MyPageDto.createWith(member));
    }
}
