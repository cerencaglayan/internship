package com.example.demo.myproject.models;

import com.example.demo.myproject.models.City;
import com.example.demo.myproject.models.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Town")
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "regionId", nullable = false)
    private Region region;

    @ManyToOne
    @JoinColumn(name = "cityId", nullable = false)
    private City city;


    public Town(String name) {
        setName(name);
    }
}
