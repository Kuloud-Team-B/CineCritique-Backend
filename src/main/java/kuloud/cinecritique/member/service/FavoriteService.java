package kuloud.cinecritique.member.service;

import kuloud.cinecritique.common.exception.CustomException;
import kuloud.cinecritique.common.exception.ErrorCode;
import kuloud.cinecritique.member.dto.FavoriteDeleteDto;
import kuloud.cinecritique.member.dto.FavoriteDto;
import kuloud.cinecritique.member.dto.FavoritePostDto;
import kuloud.cinecritique.member.entity.Favorite;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.member.repository.FavoriteRepository;
import kuloud.cinecritique.member.repository.MemberRepository;
import kuloud.cinecritique.movie.entity.Movie;
import kuloud.cinecritique.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final MovieRepository movieRepository;

    @Transactional
    public void saveFavorite(Long userId, FavoritePostDto dto) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        Movie movie = movieRepository.findByName(dto.getMovieName())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MOVIE));

        Favorite favorite = new Favorite(member, movie);
        favoriteRepository.save(favorite);
    }

    public List<FavoriteDto> getMyFavorites(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        List<Favorite> findsByMember = favoriteRepository.findByMember(member);
        return findsByMember.stream().map(FavoriteDto::new).toList();
    }

    @Transactional
    public void deleteFavorite(Long userId, FavoriteDeleteDto dto) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        Favorite favorite = favoriteRepository.findById(dto.getFavoriteId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_FAVORITE));

        if (!favorite.getMember().equals(member)) {
            throw new CustomException(ErrorCode.NOT_EXIST_FAVORITE);
        }
        favoriteRepository.delete(favorite);
    }

}
