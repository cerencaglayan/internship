package com.example.demo.myproject.mail.token;

import com.example.demo.myproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);

    List<ConfirmationToken> findAllByUser(User user);


}
