package kuloud.cinecritique.cinema.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCinema is a Querydsl query type for Cinema
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCinema extends EntityPathBase<Cinema> {

    private static final long serialVersionUID = 1736336364L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCinema cinema = new QCinema("cinema");

    public final QCompany company;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath location = createString("location");

    public final StringPath name = createString("name");

    public QCinema(String variable) {
        this(Cinema.class, forVariable(variable), INITS);
    }

    public QCinema(Path<? extends Cinema> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCinema(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCinema(PathMetadata metadata, PathInits inits) {
        this(Cinema.class, metadata, inits);
    }

    public QCinema(Class<? extends Cinema> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
    }

}

