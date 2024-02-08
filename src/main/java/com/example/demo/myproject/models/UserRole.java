package com.example.demo.myproject.models;

import jakarta.persistence.*;
import lombok.*;


@RequiredArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;



}
