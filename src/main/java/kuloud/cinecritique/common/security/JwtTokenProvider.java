package kuloud.cinecritique.common.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtEncoder jwtEncoder;

    public String createToken(Authentication authentication) {
        return jwtEncoder.encode(createJwtEncoderParams(authentication)).getTokenValue();
    }

    private JwtEncoderParameters createJwtEncoderParams(Authentication authentication) {
        return JwtEncoderParameters.from(JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(1800))
                .subject("USER")
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
