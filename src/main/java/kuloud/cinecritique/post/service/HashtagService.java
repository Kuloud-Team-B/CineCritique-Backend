package kuloud.cinecritique.post.service;

import jakarta.transaction.Transactional;
import kuloud.cinecritique.post.entity.Hashtag;
import kuloud.cinecritique.post.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class HashtagService {
    private HashtagRepository hashtagRepository;

    @Autowired
    public HashtagService(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    public Optional<Hashtag> findByTagName(String tagName) {
        return hashtagRepository.findByTagName(tagName);
    }

    @Transactional
    public Hashtag saveHashtagIfAbsent(String tagName) {
        return hashtagRepository.findByTagName(tagName)
                .orElseGet(() -> hashtagRepository.save(Hashtag.builder()
                        .tagName(tagName)
                        .posts(new HashSet<>())
                        .build()));
    }
}
