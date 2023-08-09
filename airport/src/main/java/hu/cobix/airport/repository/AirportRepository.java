package hu.cobix.airport.repository;

import hu.cobix.airport.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    Long countByIata(String iata);

    Long countByIataAndIdNot(String iata, Long id);

}
