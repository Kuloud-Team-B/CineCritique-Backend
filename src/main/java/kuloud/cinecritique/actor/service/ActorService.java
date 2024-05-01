package kuloud.cinecritique.actor.service;

import kuloud.cinecritique.actor.dto.ActorDto;
import kuloud.cinecritique.actor.entity.Actor;
import kuloud.cinecritique.actor.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActorService {
    private final ActorRepository actorRepository;

    public void saveActor(ActorDto actorDto) {
        Actor newActor = actorDto.toEntity();
        actorRepository.save(newActor);
    }

}
