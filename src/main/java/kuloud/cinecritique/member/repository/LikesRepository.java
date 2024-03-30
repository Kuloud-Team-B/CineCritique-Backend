package kuloud.cinecritique.member.repository;

import kuloud.cinecritique.member.entity.Likes;
import kuloud.cinecritique.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findByMember(Member member);
}
