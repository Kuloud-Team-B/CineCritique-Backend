package kuloud.cinecritique.common.security;

import jakarta.annotation.security.PermitAll;
import kuloud.cinecritique.common.entity.JwtResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JwtAuthenticationResourceApiController {
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/authenticate")
    @PermitAll
    public JwtResponse authenticate(Authentication authentication) {
        return new JwtResponse(jwtTokenProvider.createTokenWithAuthentication(authentication));
    }
}
