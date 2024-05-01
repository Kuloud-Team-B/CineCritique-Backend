package kuloud.cinecritique.member.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import kuloud.cinecritique.common.entity.JwtResponse;
import kuloud.cinecritique.common.security.JwtTokenUtil;
import kuloud.cinecritique.member.dto.*;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberApiController {
    private final MemberService memberService;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/sign-in")
    public JwtResponse authenticate(@RequestBody LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        MemberData memberData = memberService.signIn(email, password);
        return new JwtResponse(jwtTokenUtil.createTokenWithMemberData(memberData));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid MemberPostDto memberPostDto) {
        memberService.signUp(memberPostDto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{nickname}")
    public ResponseEntity<MemberDto> getMemberInfo(@PathVariable String nickname) {
        MemberDto result = memberService.getMemberInformation(nickname);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/checkName")
    public ResponseEntity<Void> checkDuplicatedNickname(@RequestParam String nickname) {
        memberService.checkNicknameIsDuplicated(nickname);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<Void> checkDuplicatedEmail(@RequestParam @Email String email) {
        memberService.checkEmailIsDuplicated(email);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/memberList")
    public ResponseEntity<List<MyPageDto>> getMemberList() {
        List<MyPageDto> result = memberService.getMemberList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/my-page")
    public ResponseEntity<MyPageDto> getMyPageInfo(Authentication authentication) {
        MyPageDto result = memberService.getMyPageInfo(getCurrentMemberId(authentication));
        return ResponseEntity.ok(result);
    }

    @PatchMapping
    public ResponseEntity<Void> updateMember(@RequestBody MemberPostDto memberPostDto, Authentication authentication) {
        memberService.updateMemberInfo(memberPostDto, getCurrentMemberId(authentication));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMember(Authentication authentication) {
        memberService.deleteMember(getCurrentMemberId(authentication));
        return ResponseEntity.noContent().build();
    }

    private Long getCurrentMemberId(Authentication authentication) {
        return ((Member) authentication.getPrincipal()).getId();
    }
}
