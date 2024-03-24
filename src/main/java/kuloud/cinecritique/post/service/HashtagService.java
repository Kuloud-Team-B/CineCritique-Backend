package kuloud.cinecritique.post.service;

import jakarta.transaction.Transactional;
import kuloud.cinecritique.post.entity.Hashtag;
import kuloud.cinecritique.post.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class HashtagService {
    private HashtagRepository hashtagRepository;
    public Optional<Hashtag> findByTagName(String tagName) {

        return hashtagRepository.findByTagName(tagName);
    }

    public Hashtag save(String tagName) {

        return hashtagRepository.save(
                Hashtag.builder()
                        .tagName(tagName)
                        .build());
    }
}
