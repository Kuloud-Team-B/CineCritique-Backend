package kuloud.cinecritique.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kuloud.cinecritique.post.NotFoundException;
import kuloud.cinecritique.post.dto.PostResponseDto;
import kuloud.cinecritique.post.entity.Post;
import kuloud.cinecritique.post.mapper.PostResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static kuloud.cinecritique.post.entity.QPost.post;
import static kuloud.cinecritique.member.entity.QMember.member;


@RequiredArgsConstructor
@Repository
public class PostRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory queryFactory;
    /*

    @Override
    public Page<Post> findAll(Pageable pageable) {
        JPAQuery<Post> contentQuery = queryFactory.selectFrom(post);
        List<Post> posts = contentQuery.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(post.count())
                .from(post);
        Long total = countQuery.fetchOne();

        return new PageImpl<>(posts, pageable, Optional.ofNullable(total).orElse(0L));
    }

    @Override
    public Page<Post> findAllByUser(Pageable pageable, Member member) {
        JPAQuery<Post> contentQuery = queryFactory.selectFrom(post)
                .where(post.member.eq(member));
        List<Post> posts = contentQuery.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(post.count())
                .from(post)
                .where(post.member.eq(member));
        Long total = countQuery.fetchOne();

        return new PageImpl<>(posts, pageable, Optional.ofNullable(total).orElse(0L));
    }

    @Override
    public Page<Post> findByHashtags(Pageable pageable, String hashtag) {
        //QHashtag qHashtag = QHashtag.hashtag;
        QPostHashtagMap postHashtagMap = QPostHashtagMap.postHashtagMap;

        JPAQuery<Post> contentQuery = queryFactory.selectFrom(post)
                .join(post.hashtags, postHashtagMap)
                .join(postHashtagMap.hashtag, hashtag)
                .where(hashtag.tagName.eq(hashtag));
        List<Post> posts = contentQuery.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(post.count())
                .from(post)
                .join(post.hashtags, postHashtagMap)
                .join(postHashtagMap.hashtag, qHashtag)
                .where(qHashtag.tagName.eq(hashtag));
        Long total = countQuery.fetchOne();

        return new PageImpl<>(posts, pageable, Optional.ofNullable(total).orElse(0L));
    }
    */

    @Override
    public PageImpl<PostResponseDto> getPostList(String query, Pageable pageable) {
        // Content query
        List<Post> postList = queryFactory.selectFrom(post)
                .where(post.title.containsIgnoreCase(query).or(post.content.containsIgnoreCase(query)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(post.count())
                .from(post)
                .where(post.title.contains(query))
                .fetchOne();

        List<PostResponseDto> postResponseDtoList = PostResponseMapper.INSTANCE.toDtoList(postList);

        return new PageImpl<>(postResponseDtoList, pageable, Optional.ofNullable(count).orElse(0L));
    }

    @Override
    public PostResponseDto getPostWithTag(Long id) {
        Post post1 = queryFactory.select(post)
                .from(post)
                .leftJoin(post.hashtags).fetchJoin()
                .leftJoin(post.member, member).fetchJoin()
                .where(post.Id.eq(id))
                .fetchOne();

        if (post1 == null) {
            throw new NotFoundException("Could not found post id : " + id);
        }

        return PostResponseMapper.INSTANCE.toDto(post1);
    }

    @Override
    public void addLikeCount(Post selectedPost) {
        queryFactory.update(post)
                .set(post.likeCount, post.likeCount.add(1))
                .where(post.eq(selectedPost))
                .execute();
    }

    @Override
    public void subLikeCount(Post selectedPost) {
        queryFactory.update(post)
                .set(post.likeCount, post.likeCount.subtract(1))
                .where(post.eq(selectedPost))
                .execute();
    }

    @Override
    public List<PostResponseDto> getBestList() {
        List<Post> postList = queryFactory
                .selectFrom(post)
                .orderBy(post.likeCount.desc())
                .limit(10)
                .fetch();

        return PostResponseMapper.INSTANCE.toDtoList(postList);
    }

    @Override
    public List<PostResponseDto> getTopList() {
        List<Post> postList = queryFactory
                .selectFrom(post)
                .orderBy(post.likeCount.desc())
                .limit(10)
                .fetch();

        return PostResponseMapper.INSTANCE.toDtoList(postList);
    }

    @Override
    public Page<PostResponseDto> getBestPage(int page, int size, Pageable pageable) {
        List<Post> postList = queryFactory
            .selectFrom(post)
            .orderBy(post.viewCount.desc())
            .offset((long) page * size)
            .limit(size)
            .fetch();

        Long count = queryFactory
                .select(post.count())
                .from(post)
                .fetchOne();

        // Convert List<Post> to List<PostResponseDto>
        List<PostResponseDto> postResponseDtoList = PostResponseMapper.INSTANCE.toDtoList(postList);

        return new PageImpl<>(postResponseDtoList, pageable, Optional.ofNullable(count).orElse(0L));
    }

    @Override
    public Page<PostResponseDto> getByDateRange(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        List<Post> postList = queryFactory
                .selectFrom(post)
                .where(post.createdDate.between(start, end))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(post.count())
                .from(post)
                .where(post.createdDate.between(start, end))
                .fetchOne();

        List<PostResponseDto> postResponseDtoList = PostResponseMapper.INSTANCE.toDtoList(postList);

        return new PageImpl<>(postResponseDtoList, pageable, Optional.ofNullable(total).orElse(0L));
    }
}
