package com.example.demo.myproject.service;

import com.example.demo.myproject.controller.dto.AuthenticationRequest;
import com.example.demo.myproject.controller.dto.AuthenticationResponse;
import com.example.demo.myproject.exception.PasswordNotValidException;
import com.example.demo.myproject.exception.TokenExpiredException;
import com.example.demo.myproject.exception.UserAlreadyActiveException;
import com.example.demo.myproject.exception.UserIsNotActiveException;
import com.example.demo.myproject.mail.EmailService;
import com.example.demo.myproject.mail.token.ConfirmationToken;
import com.example.demo.myproject.mail.token.ConfirmationTokenRepository;
import com.example.demo.myproject.models.User;
import com.example.demo.myproject.repository.UserRepository;
import com.example.demo.myproject.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;

    @Value("${spring.mail.username}")
    private String fromMail;


    /*
     *  1- find the user
     *  2- authenticate user with both mail and password
     *  3- create token
     *  4- create response
     * */
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        System.out.println(user.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );


        var jwtToken = jwtService.generateToken(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtToken); // Token başlığını ayarla

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(AuthenticationResponse.builder().token(jwtToken).build());
    }

    /*
     *  1- find user from email address
     *  2- send mail to provided email address
     *  3- Catch and print the error if there is any
     *
     * */
    public String activate(String email) {
        try {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                User user1 = user.get();
                if (user1.isActive()) {
                    throw new UserAlreadyActiveException("User already active in db.");
                }

                if (sendMail(user1).equals("Success")) {
                    return "";
                }

                throw new Exception("Something Unexpected in mail sending.");

            } else {
                throw new UsernameNotFoundException("The email is not in DB.");
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /*
     *  1- create token with using user
     *  2- save token to repository
     *  3- create mail message
     *  4- send link to provided mail from user
     * */
    private String sendMail(User user) {


        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        System.out.println("token is saving ...");
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom(fromMail);
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/api/v1/auth/set-password?token=" + confirmationToken.getConfirmationToken());

        emailService.sendEmail(mailMessage);
        return "Success";

    }


    /*
     *  1- control if token expired
     *  2- control if password valid
     *  3- update user from repository
     *
     * */
    public String setPassword(String confirmationToken, String password) {

        ConfirmationToken confirmationToken1 = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        try {
            if (!isTokenExpired(confirmationToken1)) {
                String result = passwordValid(password);
                if (result.equals("ok")) {

                    User user = confirmationToken1.getUser();
                    user.setPassword(passwordEncoder.encode(password));
                    user.setActive(true);
                    userRepository.save(user);
                    System.out.println("user saved\n");
                    confirmationTokenRepository.delete(confirmationToken1);
                    System.out.println("token deleted.\n");
                    return HttpStatus.OK.toString();

                } else {
                    throw new PasswordNotValidException(result);

                }

            } else {
                confirmationTokenRepository.delete(confirmationToken1);
                throw new TokenExpiredException("Token is expired.");
            }
        } catch (Exception e) {
            return e.getMessage();
        }

    }


    private String passwordValid(String password) {

        String message = "ok";
        int length = password.length();

        if (!(length >= 8 && length <= 20)) {
            message = "Password length should be between 8 and 20";
        }

        if (!Pattern.compile("[@$.!\\-+]").matcher(password).find()) {
            message = "Must have at least one special symbol among @$.!-+";
        }

        if (!password.matches(".*\\d.*")) {
            message = "Must have at least one numeric character";
        }

        if (password.equals(password.toUpperCase())) {
            message = "Must have at least one lowercase character";
        }

        if (password.equals(password.toLowerCase())) {
            message = "Must have at least one uppercase character";
        }

        return message;

    }

    private boolean isTokenExpired(ConfirmationToken token) {
        return token.getExpirationDate().before(new Date());
    }

    /*
     *  1- take the user
     *  2- check if user is active
     *  3- send mail to user's mail
     *
     * */

    public ResponseEntity<String> resetPassword(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        String message = "Email has been sent.";
        try {
            if (user.isPresent()) {
                if (user.get().isActive()) {
                    sendMail(user.get());
                    return null;

                }

                throw new UserIsNotActiveException("User is not active.");
            }
            throw new UsernameNotFoundException("User is not found.");
        } catch (Exception e) {
            message = e.getMessage();
        }

        return ResponseEntity.ok().body(message);


    }
}
