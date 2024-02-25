package kuloud.cinecritique.movie.service;

import kuloud.cinecritique.common.exception.CustomException;
import kuloud.cinecritique.common.exception.ErrorCode;
import kuloud.cinecritique.movie.dto.MovieDto;
import kuloud.cinecritique.movie.dto.MoviePostDto;
import kuloud.cinecritique.movie.entity.Movie;
import kuloud.cinecritique.movie.entity.MovieGenre;
import kuloud.cinecritique.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    @Transactional
    public ResponseEntity<Void> saveMovie(MoviePostDto moviePostDto) {
        movieRepository.save(moviePostDto.toEntity());
        return ResponseEntity.noContent().build();
    }

//    public ResponseEntity<Void> updateMovie(MoviePostDto moviePostDto) {
//        Movie findMovie = movieRepository.findByName(moviePostDto.getName())
//                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MOVIE));
//
//        return ResponseEntity.noContent().build();
//    }

    public MovieDto getMovieWithName(String name) {
        Movie findMovie = movieRepository.findByName(name)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MOVIE));
        return new MovieDto(findMovie);
    }

    public List<MovieDto> getMovieWithGenre(MovieGenre genre) {
        List<Movie> findMovies = movieRepository.findByGenre(genre);

        if (findMovies.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST_MOVIE);
        }
        return findMovies.stream().map(MovieDto::new).toList();
    }
}
