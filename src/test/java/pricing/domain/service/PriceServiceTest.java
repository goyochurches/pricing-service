package pricing.domain.service;

import pricing.domain.model.Price;
import pricing.domain.port.out.PriceRepository;
import pricing.domain.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @InjectMocks
    private PriceService priceService;

    @Mock
    private PriceRepository priceRepository;

    private static final Long PRODUCT_ID = 35455L;
    private static final Long BRAND_ID = 1L;
    private static final LocalDateTime VALID_DATE = LocalDateTime.of(2020, 6, 14, 10, 0);

    private Price createPrice(Long id, Long priceList) {
        return new Price(
                id,
                BRAND_ID,
                priceList,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59),
                new BigDecimal("25.45"),
                1
        );
    }

    @Test
    void getPrices_whenPricesExist_shouldReturnPriceList() {
        Price price1 = createPrice(1L, 1L);
        Price price2 = createPrice(2L, 2L);
        List<Price> prices = List.of(price1, price2);

        when(priceRepository.findPrices(PRODUCT_ID, BRAND_ID, VALID_DATE))
                .thenReturn(prices);

        List<Price> result = priceService.getPrices(PRODUCT_ID, BRAND_ID, VALID_DATE);

        assertThat(result)
                .hasSize(2)
                .extracting(Price::getPriceList)
                .containsExactly(1L, 2L);

        verify(priceRepository).findPrices(PRODUCT_ID, BRAND_ID, VALID_DATE);
        verifyNoMoreInteractions(priceRepository);
    }

    @Test
    void getPrices_whenSinglePrice_shouldReturnOneElement() {
        Price price = createPrice(1L, 1L);
        when(priceRepository.findPrices(PRODUCT_ID, BRAND_ID, VALID_DATE))
                .thenReturn(List.of(price));

        List<Price> result = priceService.getPrices(PRODUCT_ID, BRAND_ID, VALID_DATE);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPriceList()).isEqualTo(1L);
    }

    @Test
    void getPrices_whenNoPricesFound_shouldThrowNotFoundException() {
        when(priceRepository.findPrices(PRODUCT_ID, BRAND_ID, VALID_DATE))
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> priceService.getPrices(PRODUCT_ID, BRAND_ID, VALID_DATE))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("No price found")
                .hasMessageContaining(PRODUCT_ID.toString())
                .hasMessageContaining(BRAND_ID.toString());

        verify(priceRepository).findPrices(PRODUCT_ID, BRAND_ID, VALID_DATE);
    }

    @Test
    void getPrices_whenRepositoryThrowsException_shouldPropagateException() {
        when(priceRepository.findPrices(any(), any(), any()))
                .thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> priceService.getPrices(PRODUCT_ID, BRAND_ID, VALID_DATE))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Database error");
    }

    @Test
    void getPrices_whenValidDate_shouldCallRepositoryWithCorrectParameters() {
        Price price = createPrice(1L, 1L);
        when(priceRepository.findPrices(PRODUCT_ID, BRAND_ID, VALID_DATE))
                .thenReturn(List.of(price));

        priceService.getPrices(PRODUCT_ID, BRAND_ID, VALID_DATE);

        verify(priceRepository).findPrices(PRODUCT_ID, BRAND_ID, VALID_DATE);
    }
}