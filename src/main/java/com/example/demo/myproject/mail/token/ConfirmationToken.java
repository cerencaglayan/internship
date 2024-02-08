package com.example.demo.myproject.mail.token;

import com.example.demo.myproject.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="confirmationToken")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenid;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private Date expirationDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id")
    private User user;

    public ConfirmationToken(User userEntity) {
        this.user = userEntity;
        createdDate = new Date();
        expirationDate = (new Date(System.currentTimeMillis() + 15 * 60 * 1000));
        confirmationToken = UUID.randomUUID().toString();
    }
}