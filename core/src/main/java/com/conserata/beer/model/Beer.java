package com.conserata.beer.model;

import javax.persistence.*;
import java.util.List;

/**
 * (c) conserata IT-Consulting
 * @author tspaeth
 */
@Entity
@Table(name = "Beer")
public class Beer extends BaseEntity {
    private String brand;

    private String comment;

    @ManyToOne
    private Company company;

    private Float alcVol;

    private Long volume;

    private String companyLogoURL;

    @OneToMany(mappedBy = "beer")
    private List<Rating> ratings;

    public String getCompanyLogoURL() {
        return companyLogoURL;
    }

    public void setCompanyLogoURL(String companyLoglURL) {
        this.companyLogoURL = companyLoglURL;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Float getAlcVol() {
        return alcVol;
    }

    public void setAlcVol(Float alcVol) {
        this.alcVol = alcVol;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Beer{" +
                "brand='" + brand + '\'' +
                ", comment='" + comment + '\'' +
                ", company=" + company +
                ", alcVol=" + alcVol +
                ", volume=" + volume +
                '}';
    }
}
