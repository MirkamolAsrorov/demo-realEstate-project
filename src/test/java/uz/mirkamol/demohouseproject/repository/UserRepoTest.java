package uz.mirkamol.demohouseproject.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import uz.mirkamol.demohouseproject.model.Users;

import java.util.Optional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations = "classpath:application.yml")
class UserRepoTest {
    @Autowired
    private UserRepo userRepo;
    private Users user;

    @BeforeEach
    public void setupTestData(){
        // Given : Setup object or precondition
        user = Users.builder()
                .email("x5tJt@example.com")
                .name("test")
                .phoneNumber("123")
                .password("test")
                .build();
    }

    @Test
    @DisplayName("Saving a user is tested successfully")
    void testSave() {
        Users save = userRepo.save(user);

        Assertions.assertThat(save).isNotNull();
        Assertions.assertThat(user.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Finding Email of User is test successfully")
    void testFindByEmail() {
        userRepo.save(user);

        Optional<Users> test = userRepo.findByEmail("x5tJt@example.com");

        Assertions.assertThat(test).isPresent();

    }

    @Test
    @DisplayName("Finding the id of User is test successfully")
    void testFindById() {
        userRepo.save(user);

        Optional<Users> test = userRepo.findById(user.getId());

        Assertions.assertThat(test).isPresent();
    }
}