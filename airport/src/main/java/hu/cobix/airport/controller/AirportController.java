package hu.cobix.airport.controller;

import hu.cobix.airport.dto.AirportDto;
import hu.cobix.airport.mapper.AirportMapper;
import hu.cobix.airport.model.Airport;
import hu.cobix.airport.service.AirportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    @Autowired
    private AirportService airportService;

    @Autowired
    private AirportMapper mapper;

    @GetMapping
    public List<AirportDto> findAll(){
        List<Airport> allAirports = airportService.findAll();
        return mapper.entityListToDto(allAirports);
    }

    @PostMapping
    public AirportDto create(@RequestBody @Valid AirportDto dto){
        Airport airport = mapper.dtoToEntity(dto);
        Airport savedAirport = airportService.create(airport);
        if (savedAirport == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return mapper.entityToDto(savedAirport);
    }

    @GetMapping("/{id}")
    public AirportDto findById(@PathVariable Long id){
        Airport airport = airportService.findById(id);
        if (airport == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return mapper.entityToDto(airport);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public AirportDto update(@PathVariable long id, @RequestBody @Valid AirportDto dto/*, BindingResult bindingResult*/){
        dto = new AirportDto(id, dto.name(), dto.iata());
        Airport airport = mapper.dtoToEntity(dto);
        Airport updatedAirport = airportService.update(airport);

        if (updatedAirport == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return mapper.entityToDto(updatedAirport);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        airportService.delete(id);
    }
}
