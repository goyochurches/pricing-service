package pricing.infrastructure.adapter.in.mapper;

import pricing.domain.model.Price;
import pricing.infrastructure.adapter.in.rest.dto.PriceDTO;

public class PriceResponseMapper {

    private PriceResponseMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static PriceDTO toDTO(Price domain) {
        if (domain == null) return null;

        return PriceDTO.builder()
                .productId(domain.getProductId())
                .brandId(domain.getBrandId())
                .priceList(domain.getPriceList())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .price(domain.getPrice())
                .build();
    }
}