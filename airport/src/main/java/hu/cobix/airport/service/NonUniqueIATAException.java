package hu.cobix.airport.service;

public class NonUniqueIATAException extends RuntimeException{

    public NonUniqueIATAException(){
        super("IATA should be unique");
    }
}
