package pricing.infrastructure.adapter.out.persistence;

import pricing.domain.model.Price;
import pricing.infrastructure.adapter.out.persistence.entity.PriceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceRepositoryAdapterTest {

    @InjectMocks
    private PriceRepositoryAdapter priceRepositoryAdapter;

    @Mock
    private PriceJpaRepository jpaRepository;

    private static final Long PRODUCT_ID = 35455L;
    private static final Long BRAND_ID = 1L;
    private static final LocalDateTime VALID_DATE = LocalDateTime.of(2020, 6, 14, 10, 0);

    private PriceEntity createPriceEntity(Long id, Long priceList) {
        PriceEntity entity = new PriceEntity();
        entity.setId(id);
        entity.setBrandId(BRAND_ID);
        entity.setPriceList(priceList);
        entity.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        entity.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        return entity;
    }

    @Test
    void findPrices_whenEntitiesExist_shouldReturnMappedPrices() {
        PriceEntity entity1 = createPriceEntity(1L, 1L);
        PriceEntity entity2 = createPriceEntity(2L, 2L);
        List<PriceEntity> entities = List.of(entity1, entity2);

        when(jpaRepository.findByProductIdAndBrandIdAndDate(PRODUCT_ID, BRAND_ID, VALID_DATE))
                .thenReturn(entities);

        List<Price> result = priceRepositoryAdapter.findPrices(PRODUCT_ID, BRAND_ID, VALID_DATE);

        assertThat(result)
                .hasSize(2)
                .extracting(Price::getPriceList)
                .containsExactly(1L, 2L);

        verify(jpaRepository).findByProductIdAndBrandIdAndDate(PRODUCT_ID, BRAND_ID, VALID_DATE);
        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    void findPrices_whenNoEntitiesExist_shouldReturnEmptyList() {
        when(jpaRepository.findByProductIdAndBrandIdAndDate(PRODUCT_ID, BRAND_ID, VALID_DATE))
                .thenReturn(Collections.emptyList());

        List<Price> result = priceRepositoryAdapter.findPrices(PRODUCT_ID, BRAND_ID, VALID_DATE);

        assertThat(result).isEmpty();

        verify(jpaRepository).findByProductIdAndBrandIdAndDate(PRODUCT_ID, BRAND_ID, VALID_DATE);
    }
}