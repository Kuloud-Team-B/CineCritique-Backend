package kuloud.cinecritique.cinema.controller;

import kuloud.cinecritique.cinema.dto.CompanyDto;
import kuloud.cinecritique.cinema.dto.CompanyPostDto;
import kuloud.cinecritique.cinema.dto.CompanyUpdateDto;
import kuloud.cinecritique.cinema.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyApiController {
    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<CompanyDto> getCompany(@RequestParam String name) {
        CompanyDto result = companyService.getCompanyWithName(name);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> postCompany(@RequestBody CompanyPostDto companyPostDto) {
        companyService.saveCompany(companyPostDto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping
    public ResponseEntity<Void> updateCompany(@RequestBody CompanyUpdateDto companyUpdateDto) {
        companyService.updateCompany(companyUpdateDto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> deleteCompany(@RequestParam String name) {
        companyService.deleteCompany(name);
        return ResponseEntity.noContent().build();
    }

}
