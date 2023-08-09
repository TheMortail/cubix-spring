package hu.cobix.airport.service;

import hu.cobix.airport.model.Airport_;
import hu.cobix.airport.model.Flight;
import hu.cobix.airport.model.Flight_;
import org.springframework.data.jpa.domain.Specification;

public class FlightSpecifications {
    public static Specification<Flight> hasId (long id){
        return (root, cq, cb) -> cb.equal(root.get(Flight_.id), id);
    }

    public static Specification<Flight> flightnumberStartsWith (String prefix){
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Flight_.flightNumber)), prefix.toLowerCase() + "%");
    }
    public static Specification<Flight> flightTakeoffId (long takeoffId){
        return (root, cq, cb) -> cb.equal(root.get(Flight_.takeoff).get(Airport_.id), takeoffId);
    }
}
