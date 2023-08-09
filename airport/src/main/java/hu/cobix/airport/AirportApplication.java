package hu.cobix.airport;

import hu.cobix.airport.model.Airport;
import hu.cobix.airport.service.AirportService;
import hu.cobix.airport.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class AirportApplication implements CommandLineRunner {

	@Autowired
	PriceService priceService;

	@Autowired
	AirportService airportService;
	public static void main(String[] args) {
		SpringApplication.run(AirportApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(priceService.getFinalPrice(100));
		System.out.println(priceService.getFinalPrice(2000));
		airportService.create(new Airport(1L, "test1", "test1"));
		airportService.create(new Airport(2L, "test2", "test2"));
		airportService.createFlight(1L,2L,"123", LocalDateTime.now());

	}
}
