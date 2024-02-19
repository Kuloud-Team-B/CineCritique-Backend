package kuloud.cinecritique.member.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import kuloud.cinecritique.common.entity.JwtResponse;
import kuloud.cinecritique.common.security.JwtAuthenticationResourceApiController;
import kuloud.cinecritique.common.security.JwtTokenProvider;
import kuloud.cinecritique.member.dto.MemberDto;
import kuloud.cinecritique.member.dto.MemberPostDto;
import kuloud.cinecritique.member.dto.MyPageDto;
import kuloud.cinecritique.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/sign-in")
    @PermitAll
    public JwtResponse authenticate(@RequestParam String email,
                                    @RequestParam String password) {
        memberService.signIn(email, password);
        return new JwtResponse(jwtTokenProvider.createTokenWithEmail(email));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid MemberPostDto memberPostDto) {
        return memberService.createMemberAccountAndSave(memberPostDto);
    }

    @GetMapping("/{nickname}")
    public ResponseEntity<MemberDto> getMemberInfo(@PathVariable String nickname) {
        return memberService.getMemberInformation(nickname);
    }

    @PostMapping("/{nickname}")
    public ResponseEntity<Void> checkDuplicatedNickname(@PathVariable String nickname) {
        return memberService.checkNicknameIsDuplicated(nickname);
    }

    @GetMapping("/memberList")
    public ResponseEntity<List<MyPageDto>> getMemberList() {
        return memberService.getMemberList();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-page")
    public ResponseEntity<MyPageDto> getMyPageInfo() {
        return memberService.getMyPageInfo(getLoggedInNickname());
    }

    private String getLoggedInNickname() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
