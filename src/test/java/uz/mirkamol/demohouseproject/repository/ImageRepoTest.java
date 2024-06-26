package uz.mirkamol.demohouseproject.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.mirkamol.demohouseproject.model.Address;
import uz.mirkamol.demohouseproject.model.Images;
import uz.mirkamol.demohouseproject.model.RealEstateAd;
import uz.mirkamol.demohouseproject.model.Users;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class ImageRepoTest {

    @Autowired
    private ImageRepo imageRepo;
    @Autowired
    private RealEstateAdRepo realEstateAdRepo;
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private UserRepo userRepo;

    private Address address;
    private RealEstateAd realEstateAd;
    private Users user;
    private Images image;

    @BeforeEach
    void setUpTestData() {
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
                .publicationDate(LocalDate.now())
                .owner(user)
                .address(address)
                .build();

        image = Images.builder()
                .imageData(new byte[20])
                .type(".jpg")
                .name("image")
                .realEstateAd(realEstateAd)
                .build();
    }
    @Test
    @DisplayName("Saving Image is tested successfully")
    void testSave(){
        Images saved = imageRepo.save(image);

        assertThat(saved).isNotNull();
    }

    @Test
    @DisplayName("Finding Image By its name is tested successfully")
    void findByName() {
        userRepo.save(user);
        addressRepo.save(address);
        realEstateAdRepo.save(realEstateAd);
        imageRepo.save(image);
        Optional<Images> imageByName = imageRepo.findByName(image.getName());

        assertThat(imageByName).isPresent();
    }
}