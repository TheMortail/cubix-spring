package hu.cobix.airport.controller;

import hu.cobix.airport.dto.AirportDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AirportControllerIT {

    @Autowired
    WebTestClient webTestClient;
    String API_AIRPORTS = "/api/airports";
    @Test
    void testThatCreateAirportIsListed(){
        List<AirportDto> airportsBefore = getAllAirports();
        long id = 100;
        if (airportsBefore.size() > 0){
            id = airportsBefore.get(airportsBefore.size() - 1).id() + 1;
        }
        AirportDto newAirport = new AirportDto(id, "test name", "test iata");

        createAirport(newAirport);
        List<AirportDto> airportsAfter = getAllAirports();

        assertThat(airportsAfter.subList(0, airportsBefore.size()))
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(airportsBefore);
        assertThat(airportsAfter.get(airportsAfter.size() - 1))
                .usingRecursiveComparison()
                .isEqualTo(newAirport);

    }

    private void createAirport(AirportDto newAirport) {
        webTestClient
                .post()
                .uri(API_AIRPORTS)
                .bodyValue(newAirport)
                .exchange()
                .expectStatus().isOk();
    }

    private List<AirportDto> getAllAirports(){
        List<AirportDto> allAirports = webTestClient
                .get()
                .uri(API_AIRPORTS)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AirportDto.class)
                .returnResult()
                .getResponseBody();

        Collections.sort(allAirports, Comparator.comparing(AirportDto::id));

        return allAirports;
    }
}
