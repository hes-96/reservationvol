package de.tekup.reservationvol.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stopover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime arrivalHour;
    private LocalDateTime departureHour;
    @ManyToOne
    private Flight flight;
    @ManyToOne
    private Airport airport;
}
