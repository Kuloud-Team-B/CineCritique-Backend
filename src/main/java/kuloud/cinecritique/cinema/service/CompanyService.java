package kuloud.cinecritique.cinema.service;

import kuloud.cinecritique.cinema.dto.CompanyDto;
import kuloud.cinecritique.cinema.dto.CompanyPostDto;
import kuloud.cinecritique.cinema.dto.CompanyUpdateDto;
import kuloud.cinecritique.cinema.entity.Company;
import kuloud.cinecritique.cinema.repository.CompanyRepository;
import kuloud.cinecritique.common.exception.CustomException;
import kuloud.cinecritique.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {
    private final CompanyRepository companyRepository;

    @Transactional
    public void saveCompany(CompanyPostDto companyPostDto) {
        Company company = companyPostDto.toEntity();

        checkNameIsDuplicated(companyPostDto.getName());
        companyRepository.save(company);
    }

    public CompanyDto getCompanyWithName(String name) {
        Company company = companyRepository.findByName(name)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_COMPANY));

        return new CompanyDto(company);
    }

    public List<CompanyDto> getAllCompany() {
        List<Company> all = companyRepository.findAll();

        if (all.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST_COMPANY);
        }
        return all.stream().map(CompanyDto::new).toList();
    }

    @Transactional
    public void updateCompany(CompanyUpdateDto dto) {
        Company company = companyRepository.findByName(dto.getBeforeCompanyName())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_COMPANY));

        if (dto.getCompanyName() != null) {
            checkNameIsDuplicated(dto.getCompanyName());
        }
        company.updateInfo(dto);
    }

    @Transactional
    public void deleteCompany(String name) {
        Company company = companyRepository.findByName(name)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_COMPANY));

        companyRepository.delete(company);
    }

    public void checkNameIsDuplicated(String name) {
        if (companyRepository.findByName(name).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_COMPANY_NAME);
        }
    }
}
