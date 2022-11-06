package de.tekup.reservationvol.entities;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.Set;

public class DepartureAirport {
    @OneToMany(cascade= CascadeType.ALL, mappedBy = "departureAirport")
    private Set<Flight> flights;
}
