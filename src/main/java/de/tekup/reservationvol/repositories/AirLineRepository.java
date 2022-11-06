package de.tekup.reservationvol.repositories;

import de.tekup.reservationvol.entities.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface AirLineRepository extends JpaRepository<Airline,Long> {
}
