package kuloud.cinecritique.post.service;

import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.member.repository.MemberRepository;
import kuloud.cinecritique.post.dto.PostRequestDto;
import kuloud.cinecritique.post.entity.Post;
import kuloud.cinecritique.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    // 중복된 Member 조회 로직 제거
    private Member getAuthenticatedMember(String nickname) {
        return memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("No member found with nickname: " + nickname));
    }

    // 게시글의 권한 검사 로직 통합
    private void verifyPostOwner(Post post, String username) {
        if (!post.getMember().getNickname().equals(username)) {
            throw new IllegalStateException("You can only edit or delete your own posts.");
        }
    }

    @Transactional
    public Long createPost(PostRequestDto postDto, String nickname) {
        Member member = getAuthenticatedMember(nickname);
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .member(member)
                .build();

        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public void updatePost(Long postId, PostRequestDto postDto, String nickname) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
        verifyPostOwner(post, nickname);

        post.update(postDto.getTitle(), postDto.getContent());
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId, String nickname) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
        verifyPostOwner(post, nickname);

        postRepository.deleteById(postId);
    }
}
