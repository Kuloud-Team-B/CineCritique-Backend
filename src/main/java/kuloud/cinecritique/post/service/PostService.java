package kuloud.cinecritique.post.service;

import kuloud.cinecritique.cinema.entity.Cinema;
import kuloud.cinecritique.cinema.repository.CinemaRepository;
import kuloud.cinecritique.comment.dto.CommentResponseDto;
import kuloud.cinecritique.comment.repository.CommentRepository;
import kuloud.cinecritique.common.exception.CustomException;
import kuloud.cinecritique.common.exception.ErrorCode;
import kuloud.cinecritique.goods.entity.Goods;
import kuloud.cinecritique.goods.repository.GoodsRepository;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.member.repository.MemberRepository;
import kuloud.cinecritique.movie.entity.Movie;
import kuloud.cinecritique.movie.repository.MovieRepository;
import kuloud.cinecritique.post.NotFoundException;
import kuloud.cinecritique.post.dto.PostRequestDto;
import kuloud.cinecritique.post.dto.PostResponseDto;
import kuloud.cinecritique.post.entity.Hashtag;
import kuloud.cinecritique.post.entity.Post;
import kuloud.cinecritique.post.entity.PostHashtagMap;
import kuloud.cinecritique.post.mapper.PostRequestMapper;
import kuloud.cinecritique.post.mapper.PostResponseMapper;
import kuloud.cinecritique.post.repository.PostHashtagMapRepository;
import kuloud.cinecritique.post.repository.PostRepository;
import kuloud.cinecritique.post.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static lombok.Lombok.checkNotNull;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final MovieRepository movieRepository;
    private final CinemaRepository cinemaRepository;
    private final GoodsRepository goodsRepository;
    private final CommentRepository commentRepository;
    private final PostRequestMapper postRequestMapper;
    private final PostResponseMapper postResponseMapper;
    private final PostHashtagMapRepository postHashtagMapRepository;
    private final HashtagRepository hashtagRepository;

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        checkNotNull(id, "PostId must be provided");
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Could not found Post id : " + id));
    }

    // 모든 게시글 조회
    @Transactional(readOnly = true)
    public PageImpl<PostResponseDto> getPostList(String query, Pageable pageable) {

        if (query == null) {
            query = "";
        }

        return postRepository.getPostList(query, pageable);
    }

    // 예외 처리를 위한 공통 메소드 추가
    private <T> T findOrThrow(final Long id, JpaRepository<T, Long> repository) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not found " + "member" + " id : " + id));
    }

    // 게시글 생성
    @Transactional
    public Post create(PostRequestDto postRequestDto) {
        Member member = findOrThrow(postRequestDto.getMemberId(), memberRepository);

        Movie movie = movieRepository.findById(postRequestDto.getMovieId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MOVIE));
        Cinema cinema = cinemaRepository.findById(postRequestDto.getCinemaId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_CINEMA));
        Goods goods = goodsRepository.findById(postRequestDto.getGoodsId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_GOODS));

        Post post = postRequestMapper.toEntity(postRequestDto);
        post.updateMember(member);
        post.updateMovie(movie);
        post.updateCinema(cinema);
        post.updateGoods(goods);
        handleHashtags(postRequestDto.getHashtags(), post);

        return postRepository.save(post);
    }
    private void handleHashtags(Set<PostHashtagMap> hashtags, Post post) {
        if (hashtags != null) {
            for (PostHashtagMap tagName : hashtags) {
                Hashtag hashtag = hashtagRepository.findByTagName(tagName.toString())
                        .orElseGet(() -> hashtagRepository.save(new Hashtag(tagName.toString())));
                PostHashtagMap postHashtagMap = PostHashtagMap.create(post, hashtag);
                post.addHashtag(postHashtagMap);  // Ensure your addHashtag properly links both directions
            }
        }
    }

    // 게시글 수정
    @Transactional
    public Post update(PostRequestDto postRequestDto, Long id) {

        Post post = findById(id);

        return post.updatePost(postRequestDto, movieRepository, cinemaRepository, goodsRepository);
    }

    // 게시글 삭제
    @Transactional
    public Post delete(long id) {
        Post post = findById(id);
        postRepository.delete(post);
        return post;
    }

    @Transactional
    public PostResponseDto getPostWithTag(Long id) {
        PostResponseDto postResponseDto = postRepository.getPostWithTag(id);
        List<CommentResponseDto> commentResponseDtoList = commentRepository.findByPostId(id);
        postResponseDto.setCommentResponseDtoList(commentResponseDtoList);
        return postResponseDto;
    }

    @Transactional
    public void viewCountUp(Long postId) {
        Post post = findById(postId);
        post.viewCountUp(post);
    }

    @Transactional(readOnly = true)
    public Page<Post> getPostByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    // 태그를 사용한 검색
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPostsByTag(Hashtag tag, Pageable pageable) {
        Page<PostHashtagMap> hashtagMaps = postHashtagMapRepository.findByHashtag(tag, pageable);

        List<PostResponseDto> postDto = hashtagMaps.getContent().stream()
                .map(PostHashtagMap::getPost)
                .map(postResponseMapper::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(postDto, pageable, hashtagMaps.getTotalElements());
    }

    public List<PostResponseDto> getBestList() {
        return postRepository.getBestList();
    }
}
