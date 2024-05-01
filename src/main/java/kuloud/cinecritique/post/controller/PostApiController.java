package kuloud.cinecritique.post.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kuloud.cinecritique.post.dto.PostRequestDto;
import kuloud.cinecritique.post.dto.PostResponseDto;
import kuloud.cinecritique.post.mapper.PostResponseMapper;
import kuloud.cinecritique.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import static kuloud.cinecritique.post.HttpResponseEntity.ResponseResult;
import static kuloud.cinecritique.post.HttpResponseEntity.success;


@Slf4j
@RequiredArgsConstructor
@RestController // REST API를 위한 컨트롤러로 변경
@RequestMapping("/posts")
public class PostApiController {

    private final PostService postService;
    private final PostResponseMapper postResponseMapper;

    // 전체 게시글 조회
    @GetMapping
    public ResponseEntity<Page<PostRequestDto>> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size,
                                                            @RequestParam(defaultValue = "id") String sort) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, sort);
        Page<PostRequestDto> posts = postService.getAllPosts(pageRequest);
        return ResponseEntity.ok(posts);
    }

    // 쿼리 문자열을 사용한 검색
    @GetMapping("/search")
    public ResponseEntity<Page<PostRequestDto>> searchPosts(@RequestParam String query,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size,
                                                            @RequestParam(defaultValue = "id") String sort) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, sort);
        Page<PostRequestDto> result = postService.searchPostsByQuery(query, pageRequest);
        return ResponseEntity.ok(result);
    }

    /* 게시글 코드 Ver.1
    // 글 작성
    @PostMapping
    public ResponseEntity<Long> createPost(@RequestBody PostRequestDto postRequestDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long postId = postService.createPost(postRequestDto, username);
        return ResponseEntity.ok(postId);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        postService.updatePost(id, postRequestDto, username);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        postService.deletePost(id, postRequestDto, username);
        return ResponseEntity.noContent().build();
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        // 현재 인증된 사용자의 이름 가져오기
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 서비스 레이어에 삭제 요청 전달
        boolean deleted = postService.deletePost(id, username);

        // 삭제 성공 여부에 따라 상태 코드 반환
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
    */

    @GetMapping("/detail/{id}")
    public ResponseResult<PostResponseDto> getOne(@PathVariable(value = "id") Long id, HttpServletRequest req, HttpServletResponse res) {
        viewCountUp(id, req, res);
        return success(postService.getPostWithTag(id));
    }

    @PostMapping
    public ResponseResult<PostResponseDto> create(@RequestBody @Valid PostRequestDto postRequestDto) {
        return success(postResponseMapper.toDto(postService.create(postRequestDto)));
    }

    @PutMapping("/{id}")
    public ResponseResult<PostResponseDto> update(@PathVariable Long id,
                                                   @RequestBody @Valid PostRequestDto postRequestDto) {
        return success(postResponseMapper.toDto(postService.update(postRequestDto, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseResult<PostResponseDto> delete(@PathVariable Long id) {
        return success(postResponseMapper.toDto(postService.delete(id)));
    }


    private void viewCountUp(Long postId, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String cookieName = "postView";
        String cookieValue = Arrays.stream(cookies != null ? cookies : new Cookie[0])
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .map(Cookie::getValue)
                .orElse("");

        if (!cookieValue.contains("[" + postId + "]")) {
            postService.viewCountUp(postId);
            cookieValue += "_[" + postId + "]";
            Cookie newCookie = new Cookie(cookieName, cookieValue);
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
    }
}
