package pricing.infrastructure.adapter.in.mapper;

import pricing.domain.model.Price;
import pricing.infrastructure.adapter.in.rest.dto.PriceDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class PriceResponseMapperTest {

    @Test
    void toDTO_whenValidPrice_shouldMapAllFields() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59);

        Price price = new Price(1L, 1L, 1L, startDate, endDate,new BigDecimal("25.45"), 1);

        PriceDTO result = PriceResponseMapper.toDTO(price);

        assertThat(result).isNotNull();
        assertThat(result.getProductId()).isEqualTo(1L);
        assertThat(result.getBrandId()).isEqualTo(1L);
        assertThat(result.getPriceList()).isEqualTo(1L);
        assertThat(result.getStartDate()).isEqualTo(startDate);
        assertThat(result.getEndDate()).isEqualTo(endDate);
    }

    @Test
    void toDTO_whenNullPrice_shouldReturnNull() {
        PriceDTO result = PriceResponseMapper.toDTO(null);

        assertThat(result).isNull();
    }

    @Test
    void constructor_shouldThrowException() {
        assertThatThrownBy(() -> {
            var constructor = PriceResponseMapper.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        }).hasCauseInstanceOf(UnsupportedOperationException.class)
                .hasRootCauseMessage("Utility class");
    }
}