package pricing.domain.port.in;

import pricing.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface GetPriceUseCase {
    List<Price> getPrices(Long productId, Long brandId, LocalDateTime date);
}
