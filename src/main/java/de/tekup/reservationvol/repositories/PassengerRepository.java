package de.tekup.reservationvol.repositories;

import de.tekup.reservationvol.entities.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@RepositoryRestResource
@CrossOrigin("*")
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    @Query("SELECT p FROM Passenger p WHERE p.email = ?1")
    public Optional<Passenger> findByEmail(String email);

    @Query("SELECT p FROM Passenger p WHERE p.verificationCode = ?1")
    public Optional<Passenger> findByVerificationCode(String code);

    boolean existsByEmail(String email);
}
