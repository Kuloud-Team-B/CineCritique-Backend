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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void updateMovie(MovieUpdateDto movieUpdateDto) {
        Movie findMovie = movieRepository.findByName(movieUpdateDto.getBeforeName())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MOVIE));

        if (!findMovie.getName().equals(movieUpdateDto.getName())) {
            checkNameIsDuplicated(movieUpdateDto.getName());
            findMovie.changeName(movieUpdateDto.getName());
        }
        findMovie.changeTitleImg(movieUpdateDto.getTitleImg());
        findMovie.changeReleasedDate(movieUpdateDto.getReleasedDate());
        findMovie.changeSummary(movieUpdateDto.getSummary());
        findMovie.changeGrade(movieUpdateDto.getGrade());
        findMovie.changeGenre(movieUpdateDto.getGenre());
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

    public List<MovieDto> getAllMovie() {
        List<Movie> findMovies = movieRepository.findAll();

        if (findMovies.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST_MOVIE);
        }
        return findMovies.stream().map(MovieDto::new).toList();
    }

    @Transactional
    public void deleteMovie(String name) {
        Movie movie = movieRepository.findByName(name)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MOVIE));

        movieRepository.delete(movie);
    }
}
