package de.tekup.reservationvol.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String userName;
    private String verificationCode;
    private boolean enabled;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "passenger")
    private List<Reservation> reservations;
}
