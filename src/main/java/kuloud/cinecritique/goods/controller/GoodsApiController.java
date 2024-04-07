package kuloud.cinecritique.goods.controller;

import kuloud.cinecritique.goods.dto.GoodsDto;
import kuloud.cinecritique.goods.dto.GoodsPostDto;
import kuloud.cinecritique.goods.dto.GoodsUpdateDto;
import kuloud.cinecritique.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodsApiController {
    private final GoodsService goodsService;

    @GetMapping
    public ResponseEntity<GoodsDto> getGoods(@RequestParam String name) {
        GoodsDto result = goodsService.getGoods(name);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> saveGoods(@RequestBody GoodsPostDto dto) {
        goodsService.saveGoods(dto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping
    public ResponseEntity<Void> updateGoods(@RequestBody GoodsUpdateDto dto) {
        goodsService.updateGoods(dto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> deleteGoods(@RequestParam String name) {
        goodsService.deleteGoods(name);
        return ResponseEntity.noContent().build();
    }
}
