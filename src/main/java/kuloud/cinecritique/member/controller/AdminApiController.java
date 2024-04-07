package kuloud.cinecritique.member.controller;

import kuloud.cinecritique.common.entity.JwtResponse;
import kuloud.cinecritique.common.security.JwtTokenUtil;
import kuloud.cinecritique.member.dto.AdminLoginDto;
import kuloud.cinecritique.member.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminApiController {
    private final AdminService adminService;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public ResponseEntity<?> checkAdminAccount(@RequestParam String name) {
        adminService.checkAccountIsDuplicated(name);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public JwtResponse signIn(@RequestBody AdminLoginDto dto) {
        adminService.signIn(dto);
        return new JwtResponse(jwtTokenUtil.createAdminToken(dto));
    }
}
