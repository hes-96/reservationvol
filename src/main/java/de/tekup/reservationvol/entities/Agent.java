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
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    @OneToMany (cascade=CascadeType.ALL, mappedBy = "agent")
    private List<Reservation> reservations;

}
