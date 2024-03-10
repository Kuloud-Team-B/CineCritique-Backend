package kuloud.cinecritique.post.controller;

import kuloud.cinecritique.post.dto.PostRequestDto;
import kuloud.cinecritique.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController // REST API를 위한 컨트롤러로 변경
@RequestMapping("/api/posts") // REST API 경로 명시
public class PostApiController {

    private final PostService postService;


    @Autowired
    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Long> createPost(@RequestBody PostRequestDto postRequestDto) {
        Long postId = postService.createPost(postRequestDto, SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(postId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        postService.updatePost(id, postRequestDto, SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id, SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.noContent().build();
    }
}
