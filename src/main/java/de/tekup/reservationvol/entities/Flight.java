package de.tekup.reservationvol.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;
        private long flightNumber;
        private String departureCity;
        private String arrivalCity;
        private LocalDateTime dateOfDeparture;
        private LocalDateTime estimationOfArrivalDate;
        @ManyToOne
        private  Airline airline;
        @OneToMany (cascade=CascadeType.ALL, mappedBy = "flight")
        private List<Stopover> stopovers;
        @ManyToOne
        private Airport departureAirport;
        @ManyToOne
        private Airport arrivalAirport;
        @OneToMany (cascade=CascadeType.ALL, mappedBy = "flight")
        private List<Reservation> reservations;


}
