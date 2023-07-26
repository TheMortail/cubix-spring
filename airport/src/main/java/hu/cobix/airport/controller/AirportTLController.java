package hu.cobix.airport.controller;

import hu.cobix.airport.dto.AirportDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AirportTLController {

    private List<AirportDto> airports = new ArrayList<>();

    {
        airports.add(new AirportDto(1L, "Budapest Ferenc Liszt International", "BUD"));
    }
    @GetMapping("/")
    public String home(Map<String, Object> model){
        model.put("airports", airports);
        model.put("newAirport", new AirportDto());
        return "index";
    }

    @PostMapping("/airport")
    public String createAirport(AirportDto dto){
        airports.add(dto);
        return "redirect:/";
    }
}
