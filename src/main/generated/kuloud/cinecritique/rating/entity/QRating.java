package kuloud.cinecritique.rating.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRating is a Querydsl query type for Rating
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRating extends EntityPathBase<Rating> {

    private static final long serialVersionUID = -2137340860L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRating rating1 = new QRating("rating1");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final kuloud.cinecritique.member.entity.QMember member;

    public final kuloud.cinecritique.movie.entity.QMovie movie;

    public final NumberPath<Integer> rating = createNumber("rating", Integer.class);

    public QRating(String variable) {
        this(Rating.class, forVariable(variable), INITS);
    }

    public QRating(Path<? extends Rating> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRating(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRating(PathMetadata metadata, PathInits inits) {
        this(Rating.class, metadata, inits);
    }

    public QRating(Class<? extends Rating> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new kuloud.cinecritique.member.entity.QMember(forProperty("member")) : null;
        this.movie = inits.isInitialized("movie") ? new kuloud.cinecritique.movie.entity.QMovie(forProperty("movie")) : null;
    }

}

