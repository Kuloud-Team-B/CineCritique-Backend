ppackage kuloud.cinecritique.Movie.controller;

import kuloud.cinecritique.movie.dto.MovieDto;
import kuloud.cinecritique.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dummy")
public class MovieApiController {
    // API 시작 전 db에 모든 영화 정보 기록
    private final MovieService movieService;  // 영화 관련 서비스 레이어 주입


    // 모든 영화 데이터를 조회하는 API 엔드포인트
    @GetMapping("/all")
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        List<MovieDto> movies = movieService.getAllMovies();    // 서비스 레이어를 통해 모든 영화 데이터 조회
        return ResponseEntity.ok(movies);                       // 조회된 데이터 > HTTP 응답(200)으로 반환
    }


    // 특정 장르에 해당하는 영화 데이터를 조회하는 API 엔드포인트
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<MovieDto>> getMoviesByGenre(@PathVariable String genre) {
        List<MovieDto> movies = movieService.getMoviesByGenre(genre);   // 서비스 레이어를 통해 특정 장르의 영화 데이터 조회
        return ResponseEntity.ok(movies);                               // 조회된 데이터 > HTTP 응답(200)으로 반환
    }


    // 더미 데이터 추가를 위한 API 엔드포인트
    @GetMapping("/add-dummy")
    public ResponseEntity<?> addDummyData() throws IOException {
        File csv = new File("movies.csv");  // 영화 데이터 csv 파일이 위치한 경로 설정 필요
        BufferedReader br = new BufferedReader(new FileReader(csv));

        String line;
        boolean skipFirstLine = true;
        while ((line = br.readLine()) != null) {
            if (skipFirstLine) {
                skipFirstLine = false;
                continue;
            }

            //csv를 한 줄씩 파싱
            String[] token = line.split(",");
            Long movieId = Long.parseLong(token[0]);
            String[] genre = token[token.length - 1].split("\\|");

            StringBuilder title = new StringBuilder();
            for (int i = 1; i < token.length - 1; i++) {
                title.append(token[i]);
                if (i != token.length - 2) title.append(",");
            }

            // Movie 엔티티 생성 및 저장
            Movie movie = Movie.builder()
                    .id(movieId)
                    .name(title.toString())
                    .genre(String.join(", ", genres)) // 장르를 단일 문자열로 표현한다고 가정
                    // 나머지 필드 설정
                    .build();
            movieService.addMovie(movie);
        }

        return ResponseEntity.ok().build();
    }
}
