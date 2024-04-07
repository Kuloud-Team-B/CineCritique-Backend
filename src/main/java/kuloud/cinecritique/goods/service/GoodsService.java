package kuloud.cinecritique.goods.service;

import kuloud.cinecritique.common.exception.CustomException;
import kuloud.cinecritique.common.exception.ErrorCode;
import kuloud.cinecritique.goods.dto.GoodsDto;
import kuloud.cinecritique.goods.dto.GoodsPostDto;
import kuloud.cinecritique.goods.dto.GoodsUpdateDto;
import kuloud.cinecritique.goods.entity.Goods;
import kuloud.cinecritique.goods.repository.GoodsRepository;
import kuloud.cinecritique.movie.entity.Movie;
import kuloud.cinecritique.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodsService {
    private final GoodsRepository goodsRepository;
    private final MovieRepository movieRepository;

    @Transactional
    public void saveGoods(GoodsPostDto dto) {
        Goods goods = dto.toEntity();

        checkNameIsDuplicated(dto.getName());
        Movie movie = movieRepository.findByName(dto.getMovieName())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MOVIE));
        goods.setMovie(movie);
        goodsRepository.save(goods);
    }

    public GoodsDto getGoods(String name) {
        Goods goods = goodsRepository.findByName(name)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_GOODS));
        return new GoodsDto(goods);
    }

    @Transactional
    public void updateGoods(GoodsUpdateDto dto) {
        Goods goods = goodsRepository.findByName(dto.getBeforeName())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_GOODS));

        if (dto.getName() != null) {
            checkNameIsDuplicated(goods.getName());
        }
        if (dto.getMovieName() != null) {
            Movie movie = movieRepository.findByName(dto.getMovieName())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MOVIE));
            goods.setMovie(movie);
        }
        goods.updateInfo(dto);
    }

    @Transactional
    public void deleteGoods(String name) {
        Goods goods = goodsRepository.findByName(name)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_GOODS));
        goodsRepository.delete(goods);
    }

    public void checkNameIsDuplicated(String name) {
        if (goodsRepository.findByName(name).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_GOODS_NAME);
        }
    }
}
