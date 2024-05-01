package kuloud.cinecritique.movie.entity;

public enum MovieGrade {
    GA("전체관람가"),
    G12("12세 관람가"),
    G15("15세 관람가"),
    G18("청소년 관람불가"),
    RG("제한상영가");
/*
    GA,
    G12,
    G15,
    G18,
    RG*/
    private final String name;

    MovieGrade(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
