package pricing.infrastructure.adapter.out.persistence;

import org.springframework.stereotype.Component;
import pricing.domain.model.Price;
import pricing.domain.port.out.PriceRepository;
import pricing.infrastructure.adapter.out.mapper.PriceEntityMapper;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PriceRepositoryAdapter implements PriceRepository {

    private final PriceJpaRepository jpaRepository;

    public PriceRepositoryAdapter(PriceJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Price> findPrices(Long productId, Long brandId, LocalDateTime date) {
        return jpaRepository.findByProductIdAndBrandIdAndDate(productId, brandId, date)
                .stream()
                .map(PriceEntityMapper::toDomain)
                .toList();
    }
}