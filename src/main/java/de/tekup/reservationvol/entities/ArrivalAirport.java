package de.tekup.reservationvol.entities;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.Set;

public class ArrivalAirport extends Airport{
    @OneToMany(cascade= CascadeType.ALL, mappedBy = "arrivalAirport")
    private Set<Flight> flights;
}
