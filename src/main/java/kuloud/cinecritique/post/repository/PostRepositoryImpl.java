package kuloud.cinecritique.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.post.dto.PostResponseDto;
import kuloud.cinecritique.post.entity.Post;
import kuloud.cinecritique.post.mapper.PostResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


import kuloud.cinecritique.post.entity.QPost;
import kuloud.cinecritique.cinema.entity.QCinema;
import kuloud.cinecritique.goods.entity.QGoods;
import kuloud.cinecritique.member.entity.QMember;
import kuloud.cinecritique.movie.entity.QMovie;


@RequiredArgsConstructor
@Repository
public class PostRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final PostResponseMapper postResponseMapper;

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Post> findAllByUser(Pageable pageable, Member member) {
        return null;
    }

    @Override
    public Page<Post> findByHashtags(Pageable pageable, String hashtag) {
        return null;
    }

    @Override
    public PageImpl<PostResponseDto> getPostList(String query, Pageable pageable) {
        List<Post> postList = queryFactory
                .selectFrom(QPost.post)
                .leftJoin(QPost.post.member, QMember.member).fetchJoin()
                .leftJoin(QPost.post.movie, QMovie.movie).fetchJoin()
                .leftJoin(QPost.post.cinema, QCinema.cinema).fetchJoin()
                .leftJoin(QPost.post.goods, QGoods.goods).fetchJoin()
                .fetch();

        Long count = queryFactory
                .select(QPost.post.count())
                .from(QPost.post)
                .fetchOne();

        List<PostResponseDto> postResponseDtoList = postResponseMapper.toDtoList(postList);

        return new PageImpl<>(postResponseDtoList, pageable, count);
    }

    @SneakyThrows
    @Override
    public PostResponseDto getPostWithTag(Long id) {
        Post post = queryFactory.selectFrom(QPost.post)
                .leftJoin(QPost.post.member, QMember.member).fetchJoin()
                .leftJoin(QPost.post.movie, QMovie.movie).fetchJoin()
                .leftJoin(QPost.post.cinema, QCinema.cinema).fetchJoin()
                .leftJoin(QPost.post.goods, QGoods.goods).fetchJoin()
                .where(QPost.post.Id.eq(id))
                .fetchOne();

        if (post == null) throw new ChangeSetPersister.NotFoundException();

        return postResponseMapper.INSTANCE.toDto(post);
    }

    @Override
    public void addLikeCount(Post selectedPost) {
        queryFactory.update(QPost.post)
                .set(QPost.post.likeCount, QPost.post.likeCount.add(1))
                .where(QPost.post.eq(selectedPost))
                .execute();
    }

    @Override
    public void subLikeCount(Post selectedPost) {
        queryFactory.update(QPost.post)
                .set(QPost.post.likeCount, QPost.post.likeCount.subtract(1))
                .where(QPost.post.eq(selectedPost))
                .execute();
    }

    @Override
    public List<PostResponseDto> getBestList() {
        List<Post> postList = queryFactory
                .selectFrom(QPost.post)
                .leftJoin(QPost.post.member, QMember.member).fetchJoin()
                .leftJoin(QPost.post.movie, QMovie.movie).fetchJoin()
                .leftJoin(QPost.post.cinema, QCinema.cinema).fetchJoin()
                .leftJoin(QPost.post.goods, QGoods.goods).fetchJoin()
                .orderBy(QPost.post.likeCount.desc())
                .limit(20)
                .fetch();

        return postResponseMapper.toDtoList(postList);
    }

    @Override
    public List<Post> findByHashtag(String hashtag) {
        return List.of();
    }

    @Override
    public Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Post> findByMemberId(Long memberId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Post> findByTitleContaining(String query, Pageable pageable) {
        return null;
    }
}
