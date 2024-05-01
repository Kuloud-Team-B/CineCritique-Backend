package kuloud.cinecritique.comment.service;

import kuloud.cinecritique.comment.dto.CommentRequestDto;
import kuloud.cinecritique.comment.dto.CommentResponseDto;
import kuloud.cinecritique.comment.entity.Comment;
import kuloud.cinecritique.comment.repository.CommentRepository;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.member.repository.MemberRepository;
import kuloud.cinecritique.post.entity.Post;
import kuloud.cinecritique.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    @Autowired
    public CommentService(PostRepository postRepository, MemberRepository memberRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.commentRepository = commentRepository;
    }


    @Transactional
    public Long createComment(CommentRequestDto commentRequestDto, Long postId, String nickname) {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Member not found with nickname: " + nickname));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        // Builder 패턴 또는 생성자를 사용하여 인스턴스 생성
        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .member(member)
                .post(post)
                .build();

        comment = commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> commentList(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream()
                .map(comment -> new CommentResponseDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getMember().getNickname(),
                        comment.getPost().getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateComment(CommentRequestDto commentRequestDto, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        // 직접적인 setter 대신 update 메소드 사용
        comment.update(commentRequestDto.getContent());
    }

    @Transactional
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new RuntimeException("Comment not found with id: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }
}
