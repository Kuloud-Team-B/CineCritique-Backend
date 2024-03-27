package kuloud.cinecritique.post.service;

import jakarta.persistence.EntityNotFoundException;
import kuloud.cinecritique.post.entity.Hashtag;
import kuloud.cinecritique.post.entity.Post;
import kuloud.cinecritique.post.entity.PostHashtagMap;
import kuloud.cinecritique.post.repository.HashtagRepository;
import kuloud.cinecritique.post.repository.PostHashtagMapRepository;
import kuloud.cinecritique.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostHashtagMapService {
    private final PostRepository postRepository;
    private final HashtagRepository hashtagRepository;
    private final PostHashtagMapRepository postHashtagMapRepository;

    public void addOrUpdateHashtagsToPost(Long postId, String hashtagString) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        // 해시태그 문자열 분리 및 중복 제거
        Set<String> newHashtags = Arrays.stream(hashtagString.split(","))
                .map(String::trim)
                .map(String::toLowerCase) // 해시태그 이름 정규화
                .collect(Collectors.toSet());

        // 현재 매핑된 해시태그 조회
        Set<Hashtag> currentHashtags = postHashtagMapRepository.findAllByPost(post).stream()
                .map(PostHashtagMap::getHashtag)
                .collect(Collectors.toSet());

        // 제거할 해시태그 찾기
        currentHashtags.stream()
                .filter(hashtag -> !newHashtags.contains(hashtag.getTagName().toLowerCase()))
                .forEach(hashtag -> removeHashtagFromPost(post, hashtag));

        // 새로 추가할 해시태그 처리
        newHashtags.forEach(tagName -> {
            Hashtag hashtag = hashtagRepository.findByTagName(tagName)
                    .orElseGet(() -> hashtagRepository.save(new Hashtag(tagName)));
            if (!currentHashtags.contains(hashtag)) {
                PostHashtagMap newMap = PostHashtagMap.create(post, hashtag); // 정적 팩토리 메소드 사용
                postHashtagMapRepository.save(newMap);
            }
        });
    }

    private void removeHashtagFromPost(Post post, Hashtag hashtag) {
        PostHashtagMap map = postHashtagMapRepository.findByPostAndHashtag(post, hashtag)
                .orElseThrow(() -> new EntityNotFoundException("PostHashtagMap not found for post id: " + post.getId() + " and hashtag: " + hashtag.getTagName()));
        postHashtagMapRepository.delete(map);
    }
}
