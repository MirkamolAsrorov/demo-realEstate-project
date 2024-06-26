package uz.mirkamol.demohouseproject.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.mirkamol.demohouseproject.model.Address;
import uz.mirkamol.demohouseproject.model.RealEstateAd;
import uz.mirkamol.demohouseproject.model.Users;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class RealEstateAdRepoTest {

    @Autowired
    private RealEstateAdRepo realEstateAdRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserRepo userRepo;

    private RealEstateAd realEstateAd;
    private Address address;
    private Users user;

    @BeforeEach
    public void setupTestData() {
        // Given : Setup object or precondition
        user = Users.builder()
                .email("x5tJt@example.com")
                .name("test")
                .phoneNumber("123")
                .password("test")
                .build();
        address = Address.builder()
                .country("US")
                .city("New York")
                .distreect("San")
                .street("123 Even House")
                .build();
        realEstateAd = RealEstateAd.builder()
                .yearBuilt(1999)
                .price(1000.0)
                .type("Modern")
                .aNumberOfBeds(1)
                .aNumberOfGarage(1)
                .aNumberOfBathrooms(1)
                .sqft(122)
                .onSale(true)
                .publicationDate(LocalDate.now())
                .owner(user)
                .address(address)
                .build();
    }

    @Test
    @DisplayName("Saving Real Estate is tested successfully")
    void testSave() {
        RealEstateAd savedRealEstate = realEstateAdRepo.save(realEstateAd);

        Assertions.assertThat(savedRealEstate).isNotNull();
        Assertions.assertThat(savedRealEstate.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Finding the latest Real estate ad is tested successfully ")
    void findAllByOrderByPublicationDateDesc() {
        userRepo.save(user);
        addressRepo.save(address);
        realEstateAdRepo.save(realEstateAd);

        List<RealEstateAd> allRealEstate = realEstateAdRepo.findAllByOrderByPublicationDateDesc();

        Assertions.assertThat(allRealEstate).isNotNull();
    }

    @Test
    @DisplayName("Finding the id of the real estate is tested successfully")
    void findById() {
        RealEstateAd savedRealEstate = realEstateAdRepo.save(realEstateAd);

        Optional<RealEstateAd> allRealEstate = realEstateAdRepo.findById(savedRealEstate.getId());

        Assertions.assertThat(allRealEstate).isPresent();
    }
}