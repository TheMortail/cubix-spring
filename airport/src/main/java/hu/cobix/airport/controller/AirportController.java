package hu.cobix.airport.controller;

import hu.cobix.airport.dto.AirportDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    private Map<Long, AirportDto> airports = new HashMap<>();

    {
        airports.put(1L, new AirportDto(1L, "Budapest Ferenc Liszt International", "BUD"));
    }

    @GetMapping
    public List<AirportDto> findAll(){
        return new ArrayList<>(airports.values());
    }

    @PostMapping
    public ResponseEntity<AirportDto> create(@RequestBody AirportDto dto){
        if (airports.containsKey(dto.getId())){
            return ResponseEntity.badRequest().build();
        }
        airports.put(dto.getId(), dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportDto> findById(@PathVariable Long id){
        AirportDto airportDto = airports.get(id);
        if (airportDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(airportDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirportDto> update(@PathVariable Long id, @RequestBody AirportDto dto){
        dto.setId(id);
        if (!airports.containsKey(id)){
            return ResponseEntity.notFound().build();
        }
        airports.put(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        airports.remove(id);
    }
}
