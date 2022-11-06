package de.tekup.reservationvol.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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
        private Date dateOfDeparture;
        private Date estimationOfArrivalDate;
        @ManyToOne
        private  Airline airline;
        @OneToMany (cascade=CascadeType.ALL, mappedBy = "flight")
        private Set<Stopover> stopovers;
        @ManyToOne
        private Airport departureAirport;
        @ManyToOne
        private Airport arrivalAirport;
        @OneToMany (cascade=CascadeType.ALL, mappedBy = "flight")
        private Set<Reservation> reservations;


}
