package pricing.domain.service;

import pricing.domain.model.Price;
import pricing.domain.port.in.GetPriceUseCase;
import pricing.domain.port.out.PriceRepository;
import pricing.domain.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public class PriceService implements GetPriceUseCase {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public List<Price> getPrices(Long productId, Long brandId, LocalDateTime date) {
        List<Price> prices = priceRepository.findPrices(productId, brandId, date);

        if (prices.isEmpty()) {
            throw new NotFoundException("No price found for productId " + productId +
                    ", brandId " + brandId + " at " + date);
        }

        return prices;
    }
}