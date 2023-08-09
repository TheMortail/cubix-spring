package hu.cobix.airport.service;

import hu.cobix.airport.model.Airport;
import hu.cobix.airport.model.Flight;
import hu.cobix.airport.repository.AirportRepository;
import hu.cobix.airport.repository.FlightRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
public class AirportServiceIT {

    @Autowired
    AirportService airportService;

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    AirportRepository airportRepository;

    @BeforeEach
    public void init(){
        flightRepository.deleteAllInBatch();
        airportRepository.deleteAllInBatch();
    }

    long createAirport(long id, String name, String iata ){
        return airportRepository.save(new Airport(id, name, iata)).getId();
    }
    @Test
    void testCreateFlight() {
        //Arrange
        long takeoffId = createAirport(1L, "test1", "test1");
        long landingId = createAirport(2L, "test2", "test2");
        LocalDateTime now = LocalDateTime.now();
        String flightNumber = "123";

        //Act
        Flight flight = airportService.createFlight(takeoffId, landingId, flightNumber, now);

        //Assert
        Flight savedFlight = flightRepository.findById(flight.getId()).get();
        assertThat(savedFlight.getFlightNumber()).isEqualTo(flightNumber);
        assertThat(savedFlight.getTakeoff().getId()).isEqualTo(takeoffId);
        assertThat(savedFlight.getLanding().getId()).isEqualTo(landingId);
        assertThat(savedFlight.getTakeoffTime()).isCloseTo(now, Assertions.within(1, ChronoUnit.MICROS));
    }

    @Test
    void testFlightSearchByFlightIdAndTakeoffId(){
        long takeoffId = createAirport(1L, "test1", "test1");
        long landingId = createAirport(2L, "test2", "test2");
        Flight flight1 = airportService.createFlight(takeoffId, landingId, "ABC123", LocalDateTime.now());
        Flight flight2 = airportService.createFlight(takeoffId, landingId, "ABC234", LocalDateTime.now());
        Flight flight3 = airportService.createFlight(landingId, takeoffId, "ABC567", LocalDateTime.now());
        Flight flight4 = airportService.createFlight(takeoffId, landingId, "BBC123", LocalDateTime.now());

        Flight example = new Flight(new Airport(takeoffId, null, null), null, "ABC", null);
        List<Flight> foundFlights = airportService.findFlightByExample(example);

        assertThat(foundFlights).containsExactlyInAnyOrder(flight1, flight2);
    }

    @Test
    void testFlightSearchFlightId(){
        long takeoffId = createAirport(1L, "test1", "test1");
        long landingId = createAirport(2L, "test2", "test2");
        Flight flight1 = airportService.createFlight(takeoffId, landingId, "ABC123", LocalDateTime.now());
        Flight flight2 = airportService.createFlight(takeoffId, landingId, "ABC234", LocalDateTime.now());
        Flight flight3 = airportService.createFlight(landingId, takeoffId, "ABC567", LocalDateTime.now());
        Flight flight4 = airportService.createFlight(takeoffId, landingId, "BBC123", LocalDateTime.now());

        Flight example = new Flight(null, null, "ABC", null);
        List<Flight> foundFlights = airportService.findFlightByExample(example);

        assertThat(foundFlights).containsExactlyInAnyOrder(flight1, flight2, flight3);
    }
}
