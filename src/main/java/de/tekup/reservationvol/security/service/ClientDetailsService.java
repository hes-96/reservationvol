package de.tekup.reservationvol.security.service;

import java.util.Optional;

import de.tekup.reservationvol.entities.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.tekup.reservationvol.entities.Passenger;
import de.tekup.reservationvol.repositories.PassengerRepository;

@Service
public class ClientDetailsService implements UserDetailsService {

    @Autowired
    PassengerRepository clientRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Passenger> client = clientRepository.findByEmail(username);
        if(client.isPresent())
            return ClientDetails.build(client.get());
        else
            return null;
    }
}

