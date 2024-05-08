package kuloud.cinecritique.post;

import org.springframework.data.domain.Sort;

public class PageRequest {

    private final int page;
    private final int size;
    private final Sort.Direction direction;
    private final String sortBy;

    public PageRequest(Integer page, Integer size, Sort.Direction direction, String sortBy) {
        this.page = page <= 0 ? 1 : page;
        this.size = size;
        this.direction = direction;
        this.sortBy = sortBy;
    }

    public org.springframework.data.domain.PageRequest of() {
        return org.springframework.data.domain.PageRequest.of(page - 1, size, direction, sortBy);
    }
}
