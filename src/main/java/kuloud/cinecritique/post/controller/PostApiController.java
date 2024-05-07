package kuloud.cinecritique.post.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kuloud.cinecritique.common.exception.CustomException;
import kuloud.cinecritique.common.exception.ErrorCode;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.member.repository.MemberRepository;
import kuloud.cinecritique.post.dto.PostRequestDto;
import kuloud.cinecritique.post.dto.PostResponseDto;
import kuloud.cinecritique.post.mapper.PostResponseMapper;
import kuloud.cinecritique.post.service.PostService;
import kuloud.cinecritique.post.PageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static kuloud.cinecritique.post.HttpResponseEntity.ResponseResult;
import static kuloud.cinecritique.post.HttpResponseEntity.success;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostApiController {

    private final PostService postService;
    private final PostResponseMapper postResponseMapper;
    private final MemberRepository memberRepository;

    // 예외 처리 부분 예시
    private Member getAuthenticatedMember(String nickname) {
        return memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }
    // jwt 토큰에서 이메일 정보를 가져오는 메서드
    private String getEmailFromToken() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/best")
    public ResponseResult<?> getBestList() {
        List<PostResponseDto> bestList = postService.getBestList();
        return success(bestList);
    }

    // 전체 게시글 조회
    @GetMapping("/")
    public ResponseResult<PageImpl<PostResponseDto>> getLists(@RequestParam(value = "query", required = false) String query,
                                                                @RequestParam(value = "page", required = false) Integer page,
                                                                @RequestParam(value = "sort", required = false) String sort) {

        PageRequest pageRequest = new PageRequest(
                page == null ? 1 : page, 20, Sort.Direction.DESC, sort == null ? "id" : sort);
        PageImpl<PostResponseDto> posts = postService.getPostList(query, pageRequest.of());
        return success(posts);
    }

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
