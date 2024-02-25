package kuloud.cinecritique.movie.entity;

public enum MovieGenre {
    SF("공상과학"),
    ROMANCE("로맨스"),
    COMEDY("코미디"),
    HERO("히어로"),
    SPORT("스포츠"),
    INVESTIGATION("수사"),
    ACTION("액션"),
    WESTERN("서부극"),
    HORROR("공포"),
    NOIR("느와르"),
    DOCUMENTARY("다큐멘터리"),
    DRAMA("드라마"),
    MYSTERY("미스터리"),
    STORY("일상")
    ;

    private final String name;

    MovieGenre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
