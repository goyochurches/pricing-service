package pricing.domain.port.out;

import pricing.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository {
    List<Price> findPrices(Long productId, Long brandId, LocalDateTime date);
}
