package com.example.demo.myproject.service;

import com.example.demo.myproject.controller.dto.AuthenticationRequest;
import com.example.demo.myproject.controller.dto.AuthenticationResponse;
import com.example.demo.myproject.controller.dto.RegisterRequest;
import com.example.demo.myproject.mail.EmailService;
import com.example.demo.myproject.models.User;
import com.example.demo.myproject.repository.UserRepository;
import com.example.demo.myproject.repository.UserRoleRepository;
import com.example.demo.myproject.mail.token.ConfirmationToken;
import com.example.demo.myproject.mail.token.ConfirmationTokenRepository;
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

    This is not for demo app. rn.

    * 1- find role
    * 2- create user
    * 3- create token
    * */
    public AuthenticationResponse register(RegisterRequest request) {
        var role = roleRepository.findByName(request.getRole());
        System.out.println(role.getName() + "of");
        var user = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRole(role)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }
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
                    throw new Exception("The user is already active");
                }
                if (sendMail(user1).equals("Success")) {
                    return "Success";
                } else {
                    return "Something unexpected.";
                }
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
        System.out.println("ananınamı");


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
        if (!isTokenExpired(confirmationToken1)) {
            if (passwordValid(password)) {

                User user = confirmationToken1.getUser();
                user.setPassword(passwordEncoder.encode(password));
                user.setActive(true);
                userRepository.save(user);
                System.out.println("user saved\n");
                confirmationTokenRepository.delete(confirmationToken1);
                System.out.println("token deleted.\n");

            } else {
                System.out.println("invalid password");
            }

        } else {
            System.out.println("token is expired\n");
            confirmationTokenRepository.delete(confirmationToken1);
            System.out.println("token deleted.\n");
            return "";
        }

        return "activated";
    }


    private boolean passwordValid(String password) {

        // Rule 1: Must have at least one uppercase character
        if (password.equals(password.toLowerCase())) {
            System.out.println(" Must have at least one uppercase character.");
            return false;
        }

        // Rule 2: Must have at least one lowercase character
        if (password.equals(password.toUpperCase())) {
            System.out.println("Must have at least one lowercase character.");
            return false;
        }

        // Rule 3: Must have at least one numeric character
        if (!password.matches(".*\\d.*")) {
            System.out.println("Must have at least one numeric character.");
            return false;
        }

        // Rule 4: Must have at least one special symbol among @$.!-+
        if (!Pattern.compile("[@$.!\\-+]").matcher(password).find()) {
            System.out.println("Must have at least one special symbol among @$.!-+");
            return false;
        }

        // Rule 5: Password length should be between 8 and 20
        int length = password.length();
        System.out.println(length);
        return length >= 8 && length <= 20;
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

    public void resetPassword(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() ){
            if ( user.get().isActive()){
                sendMail(user.get());
                return;
            }
            System.out.println("User is not active");
        }
        else{
            System.out.println("Username is not in db.");
        }
    }
}
