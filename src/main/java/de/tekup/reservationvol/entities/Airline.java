package de.tekup.reservationvol.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private Integer review;
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "airline")
    private List<Flight> flights;
}
