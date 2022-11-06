package de.tekup.reservationvol.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private boolean checkedIn;
    private int nbOfBags;
    @ManyToOne
    private Flight flight;
    @ManyToOne
    private Agent agent;
    @ManyToOne
    private Passenger passenger;
}
