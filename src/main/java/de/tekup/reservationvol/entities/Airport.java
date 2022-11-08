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
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @ManyToOne
    private City city;

    @OneToMany (cascade = CascadeType.ALL, mappedBy = "airport")
    private List<Stopover> stopovers;

}
