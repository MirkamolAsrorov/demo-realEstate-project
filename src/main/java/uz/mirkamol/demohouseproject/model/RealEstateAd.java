package uz.mirkamol.demohouseproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RealEstateAd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int yearBuilt;
    private Double price;
    private String type;
    private boolean onSale = true;
    private LocalDate publicationDate = LocalDate.now();

    private int aNumberOfBeds;
    private int aNumberOfGarage;
    private int aNumberOfBathrooms;
    private int sqft;


    @ManyToOne
    private Users owner;

    @OneToOne
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "realEstateAd", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Images> images = new ArrayList<>();
}
