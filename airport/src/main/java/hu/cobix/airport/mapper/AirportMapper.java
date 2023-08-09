package hu.cobix.airport.mapper;

import hu.cobix.airport.dto.AirportDto;
import hu.cobix.airport.model.Airport;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface AirportMapper {
    AirportDto entityToDto(Airport entity);
    List<AirportDto> entityListToDto(List<Airport> airportList);
    Airport dtoToEntity(AirportDto dto);

}
