package kuloud.cinecritique.movie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMovie is a Querydsl query type for Movie
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMovie extends EntityPathBase<Movie> {

    private static final long serialVersionUID = -1394053328L;

    public static final QMovie movie = new QMovie("movie");

    public final kuloud.cinecritique.common.entity.QBaseTimeEntity _super = new kuloud.cinecritique.common.entity.QBaseTimeEntity(this);

    public final NumberPath<Double> averageRating = createNumber("averageRating", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final EnumPath<MovieGenre> genre = createEnum("genre", MovieGenre.class);

    public final EnumPath<MovieGrade> grade = createEnum("grade", MovieGrade.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final DatePath<java.time.LocalDate> releasedDate = createDate("releasedDate", java.time.LocalDate.class);

    public final StringPath summary = createString("summary");

    public final StringPath titleImg = createString("titleImg");

    public QMovie(String variable) {
        super(Movie.class, forVariable(variable));
    }

    public QMovie(Path<? extends Movie> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMovie(PathMetadata metadata) {
        super(Movie.class, metadata);
    }

}

