package kuloud.cinecritique.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtEncoder jwtEncoder;
    private final int EXPIRES_LIMIT = 1800;

    public String createTokenWithAuthentication(Authentication authentication) {
        return jwtEncoder.encode(createJwtEncoderParams(authentication)).getTokenValue();
    }

    public String createTokenWithEmail(String email) {
        return jwtEncoder.encode(createJwtEncoderParams(email)).getTokenValue();
    }

    private JwtEncoderParameters createJwtEncoderParams(Authentication authentication) {
        return JwtEncoderParameters.from(JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(EXPIRES_LIMIT))
                .subject("USER")
                .claim("scope", createClaim(authentication))
                .build()
        );
    }

    private JwtEncoderParameters createJwtEncoderParams(String email) {
        return JwtEncoderParameters.from(JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(EXPIRES_LIMIT))
                .subject(email)
                .claim("scope", "ROLE_USER")
                .build());
    }

    private String createClaim(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }

}
