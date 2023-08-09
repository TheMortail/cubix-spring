package hu.cobix.airport.service;

import hu.cobix.airport.model.Airport;
import hu.cobix.airport.model.Flight;
import hu.cobix.airport.repository.AirportRepository;
import hu.cobix.airport.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AirportService {
//    @PersistenceContext
//    EntityManager entityManager;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Transactional
    public Airport create(Airport airport){
        if (findById(airport.getId()) != null){
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
        }
        return save(airport);
    }
    @Transactional
    public Airport update(Airport airport){
        if (findById(airport.getId()) == null){
            return null;
        }
        return save(airport);
    }
    @Transactional
    public Airport save(Airport airport){
        throwIfNonUniqueIata(airport);
//        if (airport.getId() == null){
//            entityManager.persist(airport);
//        }else{
//            airport = entityManager.merge(airport);
//        }
        return airportRepository.save(airport);
    }

    public List<Airport> findAll(){
//        return entityManager.createQuery("SELECT a FROM Airport a", Airport.class).getResultList();
        return airportRepository.findAll();
    }

    public Airport findById(long id){
//        return entityManager.find(Airport.class, id);
        return airportRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(long id){
//        entityManager.remove(findById(id));
        airportRepository.deleteById(id);
    }

    private void throwIfNonUniqueIata(Airport airport){
        long count = 0;
        if (airport.getId() == 0){
//            count = (long) entityManager.createNamedQuery("Airport.countByIata")
//                    .setParameter("iata", airport.getIata())
//                    .getSingleResult();
            count = airportRepository.countByIata(airport.getIata());
        }else{
//            count = (long) entityManager.createNamedQuery("Airport.countByIataAndIdNot")
//                    .setParameter("iata", airport.getIata())
//                    .setParameter("id", airport.getId())
//                    .getSingleResult();
            count = airportRepository.countByIataAndIdNot(airport.getIata(), airport.getId());
        }

        if (count > 0){
            throw new NonUniqueIATAException();
        }
    }
    @Transactional
    public Flight createFlight(long takeoffId, long landingId, String flightNumber, LocalDateTime takeoffTime){
        Airport takeoff = airportRepository.findById(takeoffId).get();
        Airport landing = airportRepository.findById(takeoffId).get();
        Flight flight = new Flight(takeoff, landing, flightNumber, takeoffTime);
        return flightRepository.save(flight);
    }



}
