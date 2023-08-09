package hu.cobix.airport.service;

import hu.cobix.airport.model.Airport;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AirportService {
    private Map<Long, Airport> airports = new HashMap<>();

    public Airport create(Airport airport){
        if (findById(airport.getId()) != null){
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
        }
        return save(airport);
    }

    public Airport update(Airport airport){
        if (findById(airport.getId()) == null){
            return null;
        }
        return save(airport);
    }

    public Airport save(Airport airport){
        throwIfNonUniqueIata(airport);
        airports.put(airport.getId(), airport);
        return airport;
    }

    public List<Airport> findAll(){
        return new ArrayList<>(airports.values());
    }

    public Airport findById(long id){
        return airports.get(id);
    }

    public void delete(long id){
        airports.remove(id);
    }

    private void throwIfNonUniqueIata(Airport airport){
        if (airports.values().stream().anyMatch(a -> a.getIata().equals(airport.getIata()))){
            throw new NonUniqueIATAException();
        }
    }
}
