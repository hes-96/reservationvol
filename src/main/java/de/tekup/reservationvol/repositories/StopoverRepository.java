package de.tekup.reservationvol.repositories;

import de.tekup.reservationvol.entities.Stopover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface StopoverRepository extends JpaRepository<Stopover,Long> {
}
