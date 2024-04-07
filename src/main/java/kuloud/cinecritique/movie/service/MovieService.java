package kuloud.cinecritique.movie.service;

import kuloud.cinecritique.common.exception.CustomException;
import kuloud.cinecritique.common.exception.ErrorCode;
import kuloud.cinecritique.movie.dto.MovieDto;
import kuloud.cinecritique.movie.dto.MoviePostDto;
import kuloud.cinecritique.movie.dto.MovieUpdateDto;
import kuloud.cinecritique.movie.entity.Movie;
import kuloud.cinecritique.movie.entity.MovieGenre;
import kuloud.cinecritique.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    @Transactional
    public void saveMovie(MoviePostDto moviePostDto) {
        checkNameIsDuplicated(moviePostDto.getName());
        movieRepository.save(moviePostDto.toEntity());
    }

    @Transactional
    public void updateMovie(MovieUpdateDto dto) {
        Movie findMovie = movieRepository.findByName(dto.getBeforeName())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MOVIE));

        if (dto.getName() != null) {
            checkNameIsDuplicated(dto.getName());
        }
        findMovie.updateInfo(dto);
    }

    public MovieDto getMovieWithName(String name) {
        Movie findMovie = movieRepository.findByName(name)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MOVIE));
        return new MovieDto(findMovie);
    }

    public void checkNameIsDuplicated(String name) {
        if (movieRepository.findByName(name).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_MOVIE_NAME);
        }
    }

    public List<MovieDto> getMovieWithGenre(MovieGenre genre) {
        List<Movie> findMovies = movieRepository.findByGenre(genre);

        if (findMovies.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST_MOVIE);
        }
        return findMovies.stream().map(MovieDto::new).toList();
    }

    public Page<MovieDto> getAllMovie(MovieGenre genre, Pageable pageable) {
        Page<Movie> findMovies = movieRepository.findAllByGenre(genre, pageable);

        if (findMovies.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST_MOVIE);
        }
        return findMovies.map(MovieDto::new);
    }

    @Transactional
    public void deleteMovie(String name) {
        Movie movie = movieRepository.findByName(name)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MOVIE));

        movieRepository.delete(movie);
    }
}
