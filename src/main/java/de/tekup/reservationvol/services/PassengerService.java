package de.tekup.reservationvol.services;

import de.tekup.reservationvol.entities.Passenger;
import de.tekup.reservationvol.repositories.PassengerRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class PassengerService {
    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;



    public ResponseEntity<Passenger> register(Passenger passenger) throws UnsupportedEncodingException, MessagingException {
        if (passenger == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Passenger> existingUser = passengerRepository.findByEmail(passenger.getEmail());
        if (existingUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
        if (passenger.getEmail() == null || passenger.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            String encodedPassword = passwordEncoder.encode(passenger.getPassword());
            passenger.setPassword(encodedPassword);

            String randomCode = RandomString.make(64);
            passenger.setVerificationCode(randomCode);
            passenger.setEnabled(false);
            passengerRepository.save(passenger);
            sendVerificationEmail(passenger);
            return ResponseEntity.ok(passenger);
        }
    }


    private void sendVerificationEmail(Passenger passenger) throws MessagingException, UnsupportedEncodingException {

        String toAddress = passenger.getEmail();
        String fromAddress = "soumrihoussem6@gmail.com";
        String senderName = "flybooking.com";
        String subject = "Please verify your registration";
        String content = "Dear Mr/Mrs,<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "C2ouf.com";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        String verifyURL = "http://localhost:4200/verify?code=" + passenger.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

        System.out.println("Email has been sent");
    }


    public ResponseEntity<Passenger> verify(String verificationCode) {
        if (verificationCode == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Passenger> passenger = passengerRepository.findByVerificationCode(verificationCode);

        if (passenger.isPresent()) {
            passenger.get().setVerificationCode(null);
            passenger.get().setEnabled(true);
            passengerRepository.save(passenger.get());
            return ResponseEntity.ok(passenger.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public boolean existsEmail(String email) {

        return passengerRepository.existsByEmail(email);
    }


    //get client by id
    public ResponseEntity<Passenger> getPassenger(long id) {
        Optional<Passenger> optionalPassenger = passengerRepository.findById(id);
        if (optionalPassenger.isPresent()) {
            return ResponseEntity.ok(optionalPassenger.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //update client profile
    public ResponseEntity<Passenger> editProfilePassenger(long id, Passenger passenger) {
        if (passenger == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Passenger> optionalPassenger = passengerRepository.findById(id);
        if (optionalPassenger.isPresent()) {
            passenger.setId(id);
            String encodedPassword = passwordEncoder.encode(passenger.getPassword());
            passenger.setPassword(encodedPassword);
            passengerRepository.save(passenger);
            return ResponseEntity.ok(optionalPassenger.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
