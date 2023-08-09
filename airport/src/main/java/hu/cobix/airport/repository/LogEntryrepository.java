package hu.cobix.airport.repository;

import hu.cobix.airport.model.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogEntryrepository extends JpaRepository<LogEntry, Long> {
}
