package de.tekup.reservationvol.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartureAirport extends Airport{
    @OneToMany(cascade= CascadeType.ALL, mappedBy = "departureAirport")
    private List<Flight> flights;
}
