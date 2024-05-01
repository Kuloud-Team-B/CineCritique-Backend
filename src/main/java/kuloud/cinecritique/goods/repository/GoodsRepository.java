package kuloud.cinecritique.goods.repository;

import kuloud.cinecritique.goods.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
    Optional<Goods> findByName(String name);
}
