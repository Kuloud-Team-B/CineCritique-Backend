package kuloud.cinecritique.comment.controller;


import kuloud.cinecritique.comment.dto.CommentRequestDto;
import kuloud.cinecritique.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     * @param id 게시물
     * @param commentRequestDto 댓글 정보
     * @param authentication 유저 정보
     * @return 게시물 상세 페이지
     */
    @PostMapping("/board/{id}/comment")
    public String createComment(@PathVariable Long id, CommentRequestDto commentRequestDto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        commentService.createComment(commentRequestDto, id, String.valueOf(Long.valueOf(userDetails.getUsername())));

        return "redirect:/board/" + id;
    }

    /**
     * 댓글 수정
     * @param id 게시물
     * @param commentId 댓글 ID
     * @param commentRequestDto 댓글 정보
     * @return 게시물 상세 페이지
     */
    @ResponseBody
    @PostMapping("/board/{id}/comment/{commentId}/update")
    public String updateComment(@PathVariable Long id, @PathVariable Long commentId, CommentRequestDto commentRequestDto) {
        commentService.updateComment(commentRequestDto, commentId);
        return "/board/" + id;
    }

    /**
     * 댓글 삭제
     * @param id 게시물
     * @param commentId 댓글 ID
     * @return 해당 게시물 리다이렉트
     */
    @GetMapping("/board/{id}/comment/{commentId}/remove")
    public String deleteComment(@PathVariable Long id, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/board/" + id;
    }
}