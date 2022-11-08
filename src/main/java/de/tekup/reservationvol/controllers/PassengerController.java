package de.tekup.reservationvol.controllers;

import de.tekup.reservationvol.dto.PassengerDto;
import de.tekup.reservationvol.entities.Passenger;
import de.tekup.reservationvol.payload.response.JwtResponse;
import de.tekup.reservationvol.repositories.PassengerRepository;
import de.tekup.reservationvol.security.jwt.JwtUtils;
import de.tekup.reservationvol.security.service.ClientDetails;
import de.tekup.reservationvol.services.PassengerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RequestMapping("/oauth")
@RestController
@CrossOrigin("*")
public class PassengerController {
    public final static String FOUND = "FOUND";
    public final static String BAD_REQUEST = "BAD_REQUEST";
    public final static String NOT_FOUND = "NOT_FOUND";
    public final static String NULL = "ID NULL DETECTED";

    @Autowired
    PassengerService passengerService;
    @Autowired
    PassengerRepository passengerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/process_register")
    public ResponseEntity<Object> processRegister(@RequestBody PassengerDto passengerDto) throws UnsupportedEncodingException, MessagingException {
        Passenger passengerReq = modelMapper.map(passengerDto, Passenger.class);
        ResponseEntity<Passenger> passenger = passengerService.register(passengerReq);

        if (passenger.getStatusCodeValue() == 200) {
            PassengerDto userRes = modelMapper.map(passenger.getBody(), PassengerDto.class);
            return new ResponseEntity<>(userRes, HttpStatus.OK);
        } else if (passenger.getStatusCodeValue() == 400) {
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(FOUND, HttpStatus.OK);
        }
    }

    @GetMapping("/verify/{code}")
    public ResponseEntity<Object> verifyUser(@PathVariable String code) {
        ResponseEntity<Passenger> passenger = passengerService.verify(code);
        if (passenger.getStatusCodeValue() == 200) {
            PassengerDto usersDTO = modelMapper.map(passenger.getBody(), PassengerDto.class);
            return new ResponseEntity<>(usersDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND, HttpStatus.OK);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticatePassenger(@RequestBody PassengerDto passengerDto) {
        Passenger passenger = modelMapper.map(passengerDto, Passenger.class);
        if (!passengerService.existsEmail(passenger.getEmail())) {
            return new ResponseEntity<>(FOUND, HttpStatus.OK);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(passenger.getEmail(), passenger.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        ClientDetails passengerDetails = (ClientDetails) authentication.getPrincipal();
        return new ResponseEntity<>(new JwtResponse(jwt,
                passengerDetails.getId(),
                passengerDetails.getEmail()
        ), HttpStatus.OK);
    }

    //get passenger by id
    @GetMapping(value = "/getPassenger/{id}")
    public ResponseEntity<Object> getPassenger(@PathVariable("id") long id) {
        ResponseEntity<Passenger> passenger = passengerService.getPassenger(id);
        if (passenger.getStatusCodeValue() == 200) {
            PassengerDto passengerDto = modelMapper.map(passenger.getBody(), PassengerDto.class);
            return new ResponseEntity<>(passengerDto, HttpStatus.OK);
        } else if (passenger.getStatusCodeValue() == 404) {
            return new ResponseEntity<>(NOT_FOUND, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(NULL, HttpStatus.OK);

        }
    }

    //update passenger profile
    @PutMapping(value = "/editProfilePassenger/{id}")
    public ResponseEntity<Object> updateProfilePassenger(@PathVariable("id") long id, @RequestBody PassengerDto passengerDto) {
        Passenger passengerReq = modelMapper.map(passengerDto, Passenger.class);
        ResponseEntity<Passenger> passenger = passengerService.editProfilePassenger(id, passengerReq);

        if (passenger.getStatusCodeValue() == 200) {
            PassengerDto passengerRes = modelMapper.map(passenger.getBody(), PassengerDto.class);
            return new ResponseEntity<>(passengerRes, HttpStatus.OK);
        } else if(passenger.getStatusCodeValue() == 400) {
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
        } else if(passenger.getStatusCodeValue() == 404){
            return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(NULL,HttpStatus.OK);
        }
    }
    //get current passenger
    @CrossOrigin
    @GetMapping(value = "/passenger")
    public ResponseEntity<Optional<Passenger>> getCurrentPassenger(HttpServletRequest request){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Passenger> passenger = passengerRepository.findByEmail(((ClientDetails) principal).getEmail());
        if(passenger.isPresent())
            return ResponseEntity.ok(passenger);
        else
            return null;
    }
}
