package kuloud.cinecritique.member.service;

import kuloud.cinecritique.common.exception.CustomException;
import kuloud.cinecritique.common.exception.ErrorCode;
import kuloud.cinecritique.member.dto.LikesDeleteDto;
import kuloud.cinecritique.member.dto.LikesDto;
import kuloud.cinecritique.member.dto.LikesPostDto;
import kuloud.cinecritique.member.entity.Likes;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.member.repository.LikesRepository;
import kuloud.cinecritique.member.repository.MemberRepository;
import kuloud.cinecritique.post.entity.Post;
import kuloud.cinecritique.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikesService {
    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public void saveLikes(String userEmail, LikesPostDto dto) {
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));

        Likes likes = new Likes(member, post);
        likesRepository.save(likes);
    }

    public List<LikesDto> getMyLikes(String userEmail) {
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        List<Likes> findsByMember = likesRepository.findByMember(member);
        return findsByMember.stream().map(LikesDto::new).toList();
    }

    @Transactional
    public void deleteLikes(String userEmail, LikesDeleteDto dto) {
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        Likes likes = likesRepository.findById(dto.getLikesId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_LIKES));

        if (!likes.getMember().equals(member)) {
            throw new CustomException(ErrorCode.NOT_EXIST_LIKES);
        }
        likesRepository.delete(likes);
    }


}
