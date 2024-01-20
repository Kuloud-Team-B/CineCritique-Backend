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
    private final JwtEncoder jwtEncoder;

    @PostMapping("/authenticate")
    @PermitAll
    public JwtResponse authenticate(Authentication authentication) {
        return new JwtResponse(createToken(authentication));
    }

    private String createToken(Authentication authentication) {
        return jwtEncoder.encode(createJwtEncoderParams(authentication)).getTokenValue();
    }

    private JwtEncoderParameters createJwtEncoderParams(Authentication authentication) {
        return JwtEncoderParameters.from(JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(1800))
                .subject(authentication.getName())
                .claim("scope", createClaim(authentication))
                .build()
        );
    }

    private String createClaim(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }
}
