package kuloud.cinecritique.actor.dto;

import kuloud.cinecritique.actor.entity.Actor;

public record ActorDto(String name, String profileUrl) {
    public static ActorDto createWith(Actor actor) {
        return new ActorDto(actor.getName(), actor.getProfileUrl());
    }

    public Actor toEntity() {
        return Actor.builder()
                .name(this.name)
                .profileUrl(this.profileUrl)
                .build();
    }
}
