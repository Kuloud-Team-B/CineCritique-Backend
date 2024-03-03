package kuloud.cinecritique.member.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import kuloud.cinecritique.common.entity.JwtResponse;
import kuloud.cinecritique.common.security.JwtTokenProvider;
import kuloud.cinecritique.member.dto.LoginDto;
import kuloud.cinecritique.member.dto.MemberDto;
import kuloud.cinecritique.member.dto.MemberPostDto;
import kuloud.cinecritique.member.dto.MyPageDto;
import kuloud.cinecritique.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PermitAll
    @GetMapping("/sign-in")
    public JwtResponse authenticate(@RequestBody LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        memberService.signIn(email, password);
        return new JwtResponse(jwtTokenProvider.createTokenWithEmail(email));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid MemberPostDto memberPostDto) {
        memberService.signUp(memberPostDto);
        return ResponseEntity.noContent().build();
    }

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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/memberList")
    public ResponseEntity<List<MyPageDto>> getMemberList() {
        List<MyPageDto> result = memberService.getMemberList();
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-page")
    public ResponseEntity<MyPageDto> getMyPageInfo() {
        MyPageDto result = memberService.getMyPageInfo(getEmailFromToken());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping
    public ResponseEntity<Void> updateMember(@RequestBody MemberPostDto memberPostDto) {
        memberService.updateMemberInfo(memberPostDto, getEmailFromToken());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMember() {
        memberService.deleteMember(getEmailFromToken());
        return ResponseEntity.noContent().build();
    }

    private String getEmailFromToken() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
