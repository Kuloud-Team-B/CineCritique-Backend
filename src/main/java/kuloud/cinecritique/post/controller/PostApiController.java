package kuloud.cinecritique.post.controller;

import kuloud.cinecritique.post.dto.PostDto;
import kuloud.cinecritique.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@RestController // REST API를 위한 컨트롤러로 변경
@RequestMapping("/api/posts") // REST API 경로 명시
public class PostApiController {

    private final PostService postService;

    @Autowired
    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    // REST API 엔드포인트들...
    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostDto postDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        postService.createPost(postDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id, @RequestBody PostDto postDto) {
        postService.updatePost(id, postDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
