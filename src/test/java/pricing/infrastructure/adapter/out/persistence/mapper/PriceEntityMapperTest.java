package pricing.infrastructure.adapter.out.persistence.mapper;

import pricing.domain.model.Price;
import pricing.infrastructure.adapter.out.mapper.PriceEntityMapper;
import pricing.infrastructure.adapter.out.persistence.entity.PriceEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class PriceEntityMapperTest {

    @Test
    void toDomain_whenValidEntity_shouldMapAllFields() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59);

        PriceEntity entity = new PriceEntity();
        entity.setId(1L);
        entity.setBrandId(1L);
        entity.setPriceList(1L);
        entity.setStartDate(startDate);
        entity.setEndDate(endDate);
        entity.setProductId(1L);

        Price result = PriceEntityMapper.toDomain(entity);

        assertThat(result).isNotNull();
        assertThat(result.getProductId()).isEqualTo(1L);
        assertThat(result.getBrandId()).isEqualTo(1L);
        assertThat(result.getPriceList()).isEqualTo(1L);
        assertThat(result.getStartDate()).isEqualTo(startDate);
        assertThat(result.getEndDate()).isEqualTo(endDate);
    }

    @Test
    void toDomain_whenNullEntity_shouldReturnNull() {
        Price result = PriceEntityMapper.toDomain(null);

        assertThat(result).isNull();
    }

    @Test
    void toEntity_whenValidDomain_shouldMapAllFields() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59);

        Price price = new Price(
                1L,
                1L,
                1L,
                startDate,
                endDate,
                new BigDecimal("25.45"),
                1
        );

        PriceEntity result = PriceEntityMapper.toEntity(price);

        assertThat(result).isNotNull();
        assertThat(result.getBrandId()).isEqualTo(1L);
        assertThat(result.getPriceList()).isEqualTo(1L);
        assertThat(result.getStartDate()).isEqualTo(startDate);
        assertThat(result.getEndDate()).isEqualTo(endDate);
    }

    @Test
    void toEntity_whenNullDomain_shouldReturnNull() {
        PriceEntity result = PriceEntityMapper.toEntity(null);

        assertThat(result).isNull();
    }

    @Test
    void constructor_shouldThrowException() {
        assertThatThrownBy(() -> {
            var constructor = PriceEntityMapper.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        }).hasCauseInstanceOf(UnsupportedOperationException.class)
                .hasRootCauseMessage("Utility class");
    }
}