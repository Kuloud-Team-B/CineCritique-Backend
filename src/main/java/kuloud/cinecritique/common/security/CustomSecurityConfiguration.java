package kuloud.cinecritique.common.security;

import kuloud.cinecritique.member.service.AdminService;
import kuloud.cinecritique.member.service.MemberDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@RequiredArgsConstructor
public class CustomSecurityConfiguration {
    private final MemberDetailService memberDetailService;
    private final AdminService adminService;
    private final JwtTokenUtil jwtTokenUtil;

    private static final String[] WHITELIST = {
            "/authentication/**"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.addFilterBefore(new JwtAuthFilter(memberDetailService, adminService, jwtTokenUtil), UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers(WHITELIST).permitAll()
                        .anyRequest().permitAll());

        return http.build();
    }
}
