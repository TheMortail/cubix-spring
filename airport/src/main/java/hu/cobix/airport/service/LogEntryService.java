package hu.cobix.airport.service;

import hu.cobix.airport.model.Airport;
import hu.cobix.airport.model.LogEntry;
import hu.cobix.airport.repository.LogEntryrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LogEntryService {

    @Autowired
    private LogEntryrepository logEntryrepository;
    public void logAirportChange(Airport airport){
        int dataFromOtherBackend = callOtherbackendSystem();
        logEntryrepository.save(new LogEntry(String.format("Airport with id %d was modified. New name is %s. Data from other backend is %d",
                airport.getId(), airport.getName(), dataFromOtherBackend)));
    }

    public int callOtherbackendSystem(){
        int random = new Random().nextInt(4);
        if (random == 0){
            throw new RuntimeException();
        }
        return random;
    }
}
