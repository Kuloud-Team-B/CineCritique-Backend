package kuloud.cinecritique.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -1634354486L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final kuloud.cinecritique.common.entity.QBaseTimeEntity _super = new kuloud.cinecritique.common.entity.QBaseTimeEntity(this);

    public final kuloud.cinecritique.cinema.entity.QCinema cinema;

    public final NumberPath<Integer> commentCount = createNumber("commentCount", Integer.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final kuloud.cinecritique.goods.entity.QGoods goods;

    public final SetPath<PostHashtagMap, QPostHashtagMap> hashtags = this.<PostHashtagMap, QPostHashtagMap>createSet("hashtags", PostHashtagMap.class, QPostHashtagMap.class, PathInits.DIRECT2);

    public final NumberPath<Long> Id = createNumber("Id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final kuloud.cinecritique.member.entity.QMember member;

    public final kuloud.cinecritique.movie.entity.QMovie movie;

    public final StringPath postImg = createString("postImg");

    public final NumberPath<Integer> rating = createNumber("rating", Integer.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cinema = inits.isInitialized("cinema") ? new kuloud.cinecritique.cinema.entity.QCinema(forProperty("cinema"), inits.get("cinema")) : null;
        this.goods = inits.isInitialized("goods") ? new kuloud.cinecritique.goods.entity.QGoods(forProperty("goods"), inits.get("goods")) : null;
        this.member = inits.isInitialized("member") ? new kuloud.cinecritique.member.entity.QMember(forProperty("member")) : null;
        this.movie = inits.isInitialized("movie") ? new kuloud.cinecritique.movie.entity.QMovie(forProperty("movie")) : null;
    }

}

