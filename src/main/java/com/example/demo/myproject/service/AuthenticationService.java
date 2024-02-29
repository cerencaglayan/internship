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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private final MessageSource messageSource;


    @Value("${spring.mail.username}")
    private String fromMail;


    /*
     *  1- find the user
     *  2- authenticate user with both mail and password
     *  3- create token
     *  4- create response
     * */
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {


        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException(""));
        ;
        System.out.println(user.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );


        var jwtToken = jwtService.generateToken(user);
        System.out.println(jwtToken);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtToken);

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
                    throw new UserAlreadyActiveException(
                            messageSource.getMessage("error.user.exists",
                                    null, LocaleContextHolder.getLocale()));
                }

                if (sendMail(user1).equals("Success")) {
                    return messageSource.getMessage("mail.message.success",
                            null, LocaleContextHolder.getLocale());
                }

                throw new Exception(messageSource.getMessage("error.message.unexpected", null, LocaleContextHolder.getLocale()));

            } else {
                throw new UsernameNotFoundException(messageSource.getMessage("error.user.notfound",
                        null, LocaleContextHolder.getLocale()));
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
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(messageSource.getMessage("mail.message.subject", null, LocaleContextHolder.getLocale()));
        mailMessage.setFrom(fromMail);
        mailMessage.setText(messageSource.getMessage("mail.message.text", null, LocaleContextHolder.getLocale())
          //      + "http://localhost:8080/api/v1/auth/set-password/" + confirmationToken.getConfirmationToken());
           + "https://company-organization-software-lilac.vercel.app/SetNewPassword/" + confirmationToken.getConfirmationToken());
        //     + "https://delta.eu-west-1.elasticbeanstalk.com/api/v1/auth/set-password/" + confirmationToken.getConfirmationToken());
        emailService.sendEmail(mailMessage);
        return "Success";

    }


    /*
     *  1- control if token expired
     *  2- control if password valid
     *  3- update user from repository
     *
     * */
    public String setPassword(String token, String password) {

        ConfirmationToken confirmationToken1 = confirmationTokenRepository.findByConfirmationToken(token);
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
                throw new TokenExpiredException(
                        messageSource.getMessage("error.message.tokenexpired", null, LocaleContextHolder.getLocale())
                );
            }
        } catch (Exception e) {
            return e.getMessage();
        }

    }


    private String passwordValid(String password) {

        String message = "ok";
        int length = password.length();

        if (!(length >= 8 && length <= 20)) {
            message = messageSource.getMessage("password.message.length", null, LocaleContextHolder.getLocale());
        }

        if (!Pattern.compile("[@$.!\\-+]").matcher(password).find()) {
            message = messageSource.getMessage("password.message.symbol", null, LocaleContextHolder.getLocale());
        }

        if (!password.matches(".*\\d.*")) {
            message = messageSource.getMessage("password.message.numeric", null, LocaleContextHolder.getLocale());
        }

        if (password.equals(password.toUpperCase())) {
            message = messageSource.getMessage("password.message.lowercase", null, LocaleContextHolder.getLocale());
        }

        if (password.equals(password.toLowerCase())) {
            message = messageSource.getMessage("password.message.uppercase", null, LocaleContextHolder.getLocale());
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

        String message=messageSource.getMessage("mail.message.success",
                null, LocaleContextHolder.getLocale());

        try {
            if (user.isPresent()) {
                if (user.get().isActive()) {
                    sendMail(user.get());

                } else {

                    throw new UserIsNotActiveException(
                            messageSource.getMessage("error.user.notactive", null, LocaleContextHolder.getLocale())
                    );
                }
            }
            else {
                throw new UsernameNotFoundException(
                        messageSource.getMessage("error.user.notfound", null, LocaleContextHolder.getLocale())
                );
            }
        } catch (Exception e) {
            message = e.getMessage();
        }

        return ResponseEntity.ok().body(message);


    }

    public String isTokenActive(String token) {
        ConfirmationToken confirmationToken1 = confirmationTokenRepository.findByConfirmationToken(token);
        return String.valueOf(!isTokenExpired(confirmationToken1));
    }
}
