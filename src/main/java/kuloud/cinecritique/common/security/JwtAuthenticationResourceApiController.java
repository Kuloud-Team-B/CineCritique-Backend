package kuloud.cinecritique.common.security;

import jakarta.annotation.security.PermitAll;
import kuloud.cinecritique.common.entity.JwtResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JwtAuthenticationResourceApiController {
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/authenticate")
    @PermitAll
    public JwtResponse authenticate(Authentication authentication) {
        return new JwtResponse(jwtTokenProvider.createToken(authentication));
    }
}
