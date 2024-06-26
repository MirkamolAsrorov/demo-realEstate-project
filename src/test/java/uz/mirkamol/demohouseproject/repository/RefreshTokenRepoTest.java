package uz.mirkamol.demohouseproject.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class RefreshTokenRepoTest {

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;
    private RefreshToken refreshToken;

    @BeforeEach
    void setUpTestData() {
        refreshToken = RefreshToken.builder()
                .token("sdsds")
                .expiryDate(Instant.now().plus(1, ChronoUnit.DAYS))
                .build();
    }

    @Test
    @DisplayName("Saving Refresh token is tested successfully")
    void testSave(){
        RefreshToken saved = refreshTokenRepo.save(refreshToken);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isGreaterThan(0);

    }

    @Test
    @DisplayName("Finding the right token is tested successfully")
    void tokenFinder() {
        RefreshToken saved = refreshTokenRepo.save(refreshToken);
        Optional<RefreshToken> tokenedFinder = refreshTokenRepo.tokenFinder(saved.getToken());
        assertThat(tokenedFinder).isPresent();
    }
}