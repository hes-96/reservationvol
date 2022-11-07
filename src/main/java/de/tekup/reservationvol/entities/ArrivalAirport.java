package de.tekup.reservationvol.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArrivalAirport extends Airport{
    @OneToMany(cascade= CascadeType.ALL, mappedBy = "arrivalAirport")
    private Set<Flight> flights;
}
