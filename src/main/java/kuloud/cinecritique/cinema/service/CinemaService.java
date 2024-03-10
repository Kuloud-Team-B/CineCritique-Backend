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

    public void updateCinema(CinemaUpdateDto cinemaUpdateDto) {
        Cinema cinema = cinemaRepository.findByName(cinemaUpdateDto.getBeforeName())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_CINEMA));

        checkNameIsDuplicated(cinemaUpdateDto.getName());
        cinema.updateInfo(cinemaUpdateDto);
        if (!cinema.getCompany().getName().equals(cinemaUpdateDto.getCompany()) &&
                !cinemaUpdateDto.getCompany().isBlank()) {
            Company company = companyRepository.findByName(cinemaUpdateDto.getCompany())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_COMPANY));
            cinema.setCompany(company);
        }
    }

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
