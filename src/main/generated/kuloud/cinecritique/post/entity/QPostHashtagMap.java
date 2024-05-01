package kuloud.cinecritique.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostHashtagMap is a Querydsl query type for PostHashtagMap
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostHashtagMap extends EntityPathBase<PostHashtagMap> {

    private static final long serialVersionUID = -1536659366L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostHashtagMap postHashtagMap = new QPostHashtagMap("postHashtagMap");

    public final QHashtag hashtag;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPost post;

    public QPostHashtagMap(String variable) {
        this(PostHashtagMap.class, forVariable(variable), INITS);
    }

    public QPostHashtagMap(Path<? extends PostHashtagMap> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostHashtagMap(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostHashtagMap(PathMetadata metadata, PathInits inits) {
        this(PostHashtagMap.class, metadata, inits);
    }

    public QPostHashtagMap(Class<? extends PostHashtagMap> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hashtag = inits.isInitialized("hashtag") ? new QHashtag(forProperty("hashtag")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
    }

}

