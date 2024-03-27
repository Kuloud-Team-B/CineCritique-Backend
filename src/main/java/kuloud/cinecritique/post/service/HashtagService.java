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

    /**
     * 해시태그 이름으로 해시태그를 조회합니다.
     * 존재하지 않을 경우, Optional.empty()를 반환합니다.
     *
     * @param tagName 검색할 해시태그 이름
     * @return 검색된 해시태그
     */
    public Optional<Hashtag> findByTagName(String tagName) {
        return hashtagRepository.findByTagName(tagName);
    }

    /**
     * 새로운 해시태그를 저장합니다.
     * 이미 존재하는 해시태그는 새로 저장하지 않습니다.
     *
     * @param tagName 저장할 해시태그 이름
     * @return 저장된 해시태그
     */
    @Transactional
    public Hashtag saveHashtagIfAbsent(String tagName) {
        return hashtagRepository.findByTagName(tagName)
                .orElseGet(() -> hashtagRepository.save(Hashtag.builder()
                        .tagName(tagName)
                        .posts(new HashSet<>())
                        .build()));
    }
}
