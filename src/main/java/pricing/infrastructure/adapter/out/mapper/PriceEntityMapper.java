package pricing.infrastructure.adapter.out.mapper;


import pricing.domain.model.Price;
import pricing.infrastructure.adapter.out.persistence.entity.PriceEntity;

public class PriceEntityMapper {

    private PriceEntityMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Price toDomain(PriceEntity entity) {
        if (entity == null) return null;

        return Price.builder()
                .productId(entity.getProductId())
                .brandId(entity.getBrandId())
                .priceList(entity.getPriceList())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .price(entity.getPrice())
                .priority(entity.getPriority())
                .build();
    }

    public static PriceEntity toEntity(Price domain) {
        if (domain == null) return null;

        PriceEntity entity = new PriceEntity();
        entity.setProductId(domain.getProductId());
        entity.setBrandId(domain.getBrandId());
        entity.setPriceList(domain.getPriceList());
        entity.setStartDate(domain.getStartDate());
        entity.setEndDate(domain.getEndDate());
        entity.setPrice(domain.getPrice());
        entity.setPriority(domain.getPriority());

        return entity;
    }
}
