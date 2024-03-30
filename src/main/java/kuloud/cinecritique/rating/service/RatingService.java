package kuloud.cinecritique.rating.service;

import kuloud.cinecritique.movie.repository.MovieRepository;
import kuloud.cinecritique.rating.dto.RatingRequestDto;
import kuloud.cinecritique.rating.entity.Rating;
import kuloud.cinecritique.member.repository.MemberRepository;
import kuloud.cinecritique.rating.repository.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RatingService {
    // Repositories and constructor
    private final MovieRepository movieRepository;
    private final  MemberRepository memberRepository;
    private final  RatingRepository ratingRepository;

    public RatingService(MovieRepository movieRepository, MemberRepository memberRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.memberRepository = memberRepository;
        this.ratingRepository = ratingRepository;
    }

    @Transactional
    public Rating addRating(RatingRequestDto ratingRequestDto, Long memberId) {
        var movie = movieRepository.findById(ratingRequestDto.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));
        var member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Rating rating = new Rating(movie, member, ratingRequestDto.getRating());
        return ratingRepository.save(rating);
    }
}
