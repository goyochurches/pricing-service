package pricing.infrastructure.config;

import pricing.domain.port.out.PriceRepository;
import pricing.domain.service.PriceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PriceService priceService(PriceRepository priceRepository) {
        return new PriceService(priceRepository);
    }
}