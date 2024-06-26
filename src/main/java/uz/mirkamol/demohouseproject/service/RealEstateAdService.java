package uz.mirkamol.demohouseproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mirkamol.demohouseproject.exception.CustomNotFoundException;
import uz.mirkamol.demohouseproject.model.Address;
import uz.mirkamol.demohouseproject.model.RealEstateAd;
import uz.mirkamol.demohouseproject.model.Users;
import uz.mirkamol.demohouseproject.payload.ApiResponse;
import uz.mirkamol.demohouseproject.payload.RealEstateAdRequest;
import uz.mirkamol.demohouseproject.repository.AddressRepo;
import uz.mirkamol.demohouseproject.repository.RealEstateAdRepo;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RealEstateAdService {
    private final RealEstateAdRepo realEstateAdRepo;
    private final AddressRepo addressRepo;
    private final UserService userService;

    public ApiResponse add(Principal principal, RealEstateAdRequest adRequest) {
        var realEstate = new RealEstateAd();
        realEstate.setYearBuilt(adRequest.getYearBuilt());
        realEstate.setPrice(adRequest.getPrice());
        realEstate.setType(adRequest.getType());
        realEstate.setANumberOfBeds(adRequest.getANumberOfBeds());
        realEstate.setANumberOfBathrooms(adRequest.getANumberOfBathrooms());
        realEstate.setANumberOfGarage(adRequest.getANumberOfGarage());
        realEstate.setSqft(adRequest.getSqft());

        var address = new Address();
        address.setStreet(adRequest.getAddress().getStreet());
        address.setDistreect(adRequest.getAddress().getDistreect());
        address.setCity(adRequest.getAddress().getCity());
        address.setCountry(adRequest.getAddress().getCountry());
        addressRepo.save(address);
        realEstate.setAddress(address);

        Users owner = userService.getUserByEmail(principal.getName());
        realEstate.setOwner(owner);

        realEstateAdRepo.save(realEstate);
        return ApiResponse.builder()
                .message("Property added")
                .success(true)
                .data(realEstate)
                .build();
    }


    public ApiResponse getAll() {
        List<RealEstateAd> realEstateAd = realEstateAdRepo.findAllByOrderByPublicationDateDesc();
        if (realEstateAd.isEmpty()) {
           throw new CustomNotFoundException("List of real estate is empty");
        }

        return ApiResponse.builder()
                .message("List of real estate")
                .success(true)
                .data(realEstateAd)
                .build();
    }
}
