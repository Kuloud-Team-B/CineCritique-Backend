package kuloud.cinecritique.movie.service;

import kuloud.cinecritique.common.exception.CustomException;
import kuloud.cinecritique.common.exception.ErrorCode;
import kuloud.cinecritique.movie.dto.MovieDto;
import kuloud.cinecritique.movie.entity.Movie;
import kuloud.cinecritique.movie.entity.MovieGenre;
import kuloud.cinecritique.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    // 영화 이름으로 영화 정보를 조회하여 MovieDto 반환
    public MovieDto getMovieWithName(String name) {
        Movie findMovie = movieRepository.findByName(name)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MOVIE));
        return new MovieDto(findMovie);
    }

    // 장르로 영화를 조회하여 해당하는 모든 영화의 MovieDto 리스트 반환
    public List<MovieDto> getMovieWithGenre(String genre) {
        List<Movie> findMovies = movieRepository.findByGenre(MovieGenre.valueOf(genre));

        if (findMovies.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST_MOVIE);
        }
        return findMovies.stream().map(MovieDto::new).toList();
    }


    // 더미 데이터 추가 (readOnly = false로 튼랜잭션 설정 변경)
    @Transactional(readOnly = false)
    public void addMovie(Movie movie) {
        movieRepository.save(movie);
    }

    // 모든 영화 정보 조회하여 MovieDto 리스트로 반환
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream().map(movie -> new MovieDto(
                movie.getName(),
                movie.getTitleImg(),
                movie.getReleasedDate(),
                movie.getSummary(),
                movie.getGrade().getName(),
                movie.getGenre().getName()
        )).collect(Collectors.toList());
    }

    
    /*영화 추천 로직
    * 사용자의 영화 선택에 따라 가장 인기 있는 장르를 결정, 이를 기반으로 영화 추천  */

    // 페이지네이션을 지원하는 영화 목록 검색
    public ResponseEntity<?> retrieveMovies(Pageable pageable) {
        Page<Movie> moviesPage = movieRepository.findAll(pageable);
        return new ResponseEntity<>(moviesPage.map(MovieDto::new), HttpStatus.OK);
    }

    // 사용자의 영화 선택에 따른 장르를 집계
    public HashMap<MovieGenre, Integer> getPickedGenres(RecommendationDto recommendationDto) {
        HashMap<MovieGenre, Integer> pickedGenres = new HashMap<>();
        recommendationDto.getPickedMovies().forEach(movieId -> {
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MOVIE));

            movie.getGenres().forEach(genre -> {
                //pickedGenres.put(genre, pickedGenres.getOrDefault(genre, 0) + 1);
                pickedGenres.merge(genre, 1, Integer::sum); // Merge를 이용해 카운트 증가
            });
        });

        return pickedGenres;
    }

    // 집계된 장르에서 최상위 두 개를 선택
    public Set<MovieGenre> selectTopGenres(HashMap<MovieGenre, Integer> genreCountMap) {
        return genreCountMap.entrySet().stream()
                .sorted(Map.Entry.<MovieGenre, Integer>comparingByValue().reversed())
                .limit(2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    // 추천 영화 검색
    public List<MovieDto> getRecommendation(RecommendationDto recommendationDto) {
        Set<MovieGenre> topGenres = selectTopGenres(getPickedGenres(recommendationDto));
        /*return movieRepository.findAllByGenresIn(topGenres).stream()
                .map(MovieDto::new)
                .collect(Collectors.toList());*/
        return topGenres.stream()
                .flatMap(genre -> movieRepository.findByGenre(genre).stream())
                .map(MovieDto::new)
                .collect(Collectors.toList());
    }
}
