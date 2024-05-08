package kuloud.cinecritique.post.controller;

import kuloud.cinecritique.post.entity.Hashtag;
import kuloud.cinecritique.post.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hashtags")
@RequiredArgsConstructor
public class HashtagController {
    private final HashtagService hashtagService;

    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> autocompleteHashtags(@RequestParam String query) {
        List<String> hashtagNames = hashtagService.findByTagName(query).stream()
                .map(Hashtag::getTagName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(hashtagNames);
    }
}
