package kuloud.cinecritique.cinema.service;

import kuloud.cinecritique.cinema.dto.CinemaDto;
import kuloud.cinecritique.cinema.dto.CinemaPostDto;
import kuloud.cinecritique.cinema.dto.CinemaUpdateDto;
import kuloud.cinecritique.cinema.entity.Cinema;
import kuloud.cinecritique.cinema.entity.Company;
import kuloud.cinecritique.cinema.repository.CinemaRepository;
import kuloud.cinecritique.cinema.repository.CompanyRepository;
import kuloud.cinecritique.common.exception.CustomException;
import kuloud.cinecritique.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public void saveCinema(CinemaPostDto cinemaPostDto) {
        Cinema cinema = cinemaPostDto.toEntity();

        checkNameIsDuplicated(cinemaPostDto.getName());
        Company company = companyRepository.findByName(cinemaPostDto.getCompany())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_COMPANY));
        cinema.setCompany(company);
        cinemaRepository.save(cinema);
    }

    public CinemaDto getCinema(String name) {
        Cinema cinema = cinemaRepository.findByName(name)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_CINEMA));

        return new CinemaDto(cinema);
    }

    @Transactional
    public void updateCinema(CinemaUpdateDto dto) {
        Cinema cinema = cinemaRepository.findByName(dto.getBeforeName())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_CINEMA));

        if (dto.getName() != null) {
            checkNameIsDuplicated(dto.getName());
        }
        cinema.updateInfo(dto);
    }

    @Transactional
    public void deleteCinema(String name) {
        Cinema cinema = cinemaRepository.findByName(name)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_CINEMA));

        cinemaRepository.delete(cinema);
    }

    public void checkNameIsDuplicated(String name) {
        if (cinemaRepository.findByName(name).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_CINEMA_NAME);
        }
    }

}
