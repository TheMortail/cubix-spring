package hu.cobix.airport.config;

import hu.cobix.airport.service.DiscountService;
import hu.cobix.airport.service.SpecialDiscountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdConfiguration {
    @Bean
    public DiscountService discountService(){
        return new SpecialDiscountService();
    }
}
