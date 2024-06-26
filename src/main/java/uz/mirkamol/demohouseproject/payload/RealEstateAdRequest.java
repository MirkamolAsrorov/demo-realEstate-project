package uz.mirkamol.demohouseproject.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.mirkamol.demohouseproject.model.Address;


@Getter
@Setter
@Builder
public class RealEstateAdRequest {
    @NotNull
    private int yearBuilt;

    @NotNull
    private Double price;
    @NotBlank
    private String type;

    @NotNull
    private int aNumberOfBeds;
    @NotNull
    private int aNumberOfGarage;
    @NotNull
    private int aNumberOfBathrooms;
    @NotNull
    private int sqft;

    @NotNull
    private final Address address;

}
