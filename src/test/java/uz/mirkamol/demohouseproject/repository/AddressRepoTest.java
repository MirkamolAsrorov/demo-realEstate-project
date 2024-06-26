package uz.mirkamol.demohouseproject.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.mirkamol.demohouseproject.model.Address;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class AddressRepoTest {

    @Autowired
    private AddressRepo addressRepo;
    private Address address;

    @BeforeEach
    void setUpTestData() {
        address = Address.builder()
                .country("US")
                .city("New York")
                .distreect("San")
                .street("123 Even House")
                .build();
    }

    @Test
    @DisplayName("Saving Address is tested successfully")
    void testSave() {
        Address savedAddress = addressRepo.save(address);
        Assertions.assertThat(savedAddress).isNotNull();
    }
}