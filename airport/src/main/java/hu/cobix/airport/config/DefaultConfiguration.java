package hu.cobix.airport.config;

import hu.cobix.airport.service.DefaultDiscountService;
import hu.cobix.airport.service.DiscountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!prod")
public class DefaultConfiguration {
    @Bean
    public DiscountService discountService(){
        return new DefaultDiscountService();
    }
}
