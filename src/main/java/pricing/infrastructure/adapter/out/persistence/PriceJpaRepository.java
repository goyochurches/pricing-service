package pricing.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pricing.infrastructure.adapter.out.persistence.entity.PriceEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p FROM PriceEntity p WHERE p.productId = :productId " +
            "AND p.brandId = :brandId " +
            "AND :date BETWEEN p.startDate AND p.endDate")
    List<PriceEntity> findByProductIdAndBrandIdAndDate(
            @Param("productId") Long productId,
            @Param("brandId") Long brandId,
            @Param("date") LocalDateTime date);
}
