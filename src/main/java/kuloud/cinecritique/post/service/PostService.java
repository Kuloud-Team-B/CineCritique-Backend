package kuloud.cinecritique.post.service;

import kuloud.cinecritique.post.dto.PostDto;
import kuloud.cinecritique.post.entity.Post;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.member.repository.MemberRepository;
import kuloud.cinecritique.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Post createPost(PostDto postDto) {
        /* 로그인 된 사용자만 작성이 가능하도록
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nickname = authentication.getName();
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("No member found with nickname: " + nickname));
        */
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                // .member(member)
                .build();

        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (!post.getMember().getNickname().equals(username)) {
            throw new IllegalStateException("You can only edit your own posts.");
        }

        post.update(postDto.getTitle(), postDto.getContent());
        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (!post.getMember().getNickname().equals(username)) {
            throw new IllegalStateException("You can only delete your own posts.");
        }

        postRepository.delete(post);
    }



    @Transactional(readOnly = true)
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream().map(post -> new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember() != null ? post.getMember().getId() : null,
                post.getMember() != null ? post.getMember().getNickname() : null,
                post.getPostImg(),
                post.getRating(),
                post.getHashtag()
        )).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember().getId(),
                post.getMember().getNickname(),
                post.getPostImg(),
                post.getRating(),
                post.getHashtag());
    }
}
