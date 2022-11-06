package de.tekup.reservationvol.repositories;

import de.tekup.reservationvol.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
