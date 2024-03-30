package kuloud.cinecritique.member.controller;

import kuloud.cinecritique.member.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminApiController {
    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<?> checkAdminAccount(@RequestParam String name) {
        adminService.checkAccountIsDuplicated(name);
        return ResponseEntity.noContent().build();
    }
}
