package com.example.demo.myproject.townpackage;

import com.example.demo.myproject.citypackage.City;
import com.example.demo.myproject.regionpackage.Region;
import jakarta.persistence.*;

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


    public Town() {
    }

    public Town(Integer id, String name, Region region, City city) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
