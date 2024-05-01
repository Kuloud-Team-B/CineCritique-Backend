package kuloud.cinecritique.common.security;

import kuloud.cinecritique.common.exception.CustomException;
import kuloud.cinecritique.common.exception.ErrorCode;
import kuloud.cinecritique.member.dto.AdminLoginDto;
import kuloud.cinecritique.member.dto.MemberData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenUtil {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final int EXPIRES_LIMIT = 1800;

    public String createTokenWithMemberData(MemberData memberData) {
        return jwtEncoder.encode(createJwtEncoderParams(memberData)).getTokenValue();
    }

    private JwtEncoderParameters createJwtEncoderParams(MemberData memberData) {
        return JwtEncoderParameters.from(JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(EXPIRES_LIMIT))
                .claim("memberId", memberData.getMemberId())
                .claim("email", memberData.getEmail())
                .claim("role", memberData.getRole())
                .build());
    }

    public String createAdminToken(AdminLoginDto dto) {
        return jwtEncoder.encode(
                JwtEncoderParameters.from(JwtClaimsSet.builder()
                        .issuer("self")
                        .issuedAt(Instant.now())
                        .expiresAt(Instant.now().plusSeconds(EXPIRES_LIMIT))
                        .claim("role", "ADMIN")
                        .claim("name", dto.getName())
                        .build())
        ).getTokenValue();
    }

    public boolean isAdminToken(String token) {
        return parseClaims(token).getClaim("role").equals("ADMIN");
    }

    public String getMemberId(String token) {
        return parseClaims(token).getClaim("memberId");
    }

    public String getAdminName(String token) {
        return parseClaims(token).getClaim("name");
    }

    public boolean validateToken(String token) {
        try {
            jwtDecoder.decode(token);
            return true;
        } catch (JwtException e) {
            log.info(e.getMessage());
        }
        return false;
    }

    public Jwt parseClaims(String accessToken) {
        try {
            return jwtDecoder.decode(accessToken);
        } catch (JwtException e) {
            log.info(e.getMessage());
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

}
