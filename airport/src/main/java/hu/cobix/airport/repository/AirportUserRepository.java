package hu.cobix.airport.repository;

import hu.cobix.airport.model.AirportUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportUserRepository extends JpaRepository<AirportUser, String> {
}
