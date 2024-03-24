package kuloud.cinecritique.post.controller;

import kuloud.cinecritique.post.entity.Hashtag;
import kuloud.cinecritique.post.repository.HashtagRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hashtags")
public class HashtagController {
    private final HashtagRepository hashtagRepository;

    public HashtagController(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> autocompleteHashtags(@RequestParam String query) {
        List<Hashtag> hashtags = hashtagRepository.findByHashtagContaining(query);
        List<String> hashtagNames = hashtags.stream()
                .map(Hashtag::getHashtag)
                .collect(Collectors.toList());
        return ResponseEntity.ok(hashtagNames);
    }
}
