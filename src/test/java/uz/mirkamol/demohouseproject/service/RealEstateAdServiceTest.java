package uz.mirkamol.demohouseproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.mirkamol.demohouseproject.exception.CustomNotFoundException;
import uz.mirkamol.demohouseproject.model.Address;
import uz.mirkamol.demohouseproject.model.RealEstateAd;
import uz.mirkamol.demohouseproject.model.Users;
import uz.mirkamol.demohouseproject.payload.ApiResponse;
import uz.mirkamol.demohouseproject.payload.RealEstateAdRequest;
import uz.mirkamol.demohouseproject.repository.AddressRepo;
import uz.mirkamol.demohouseproject.repository.RealEstateAdRepo;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Nested
@ExtendWith(MockitoExtension.class)
class RealEstateAdServiceTest {

    @InjectMocks
    private RealEstateAdService underTest;

    @Mock
    private UserService userService;

    @Mock
    private RealEstateAdRepo realEstateAdRepo;
    @Mock
    private AddressRepo addressRepo;

    @Mock
    private Users user;
    @Mock
    private Address address;
    @Mock
    private RealEstateAd realEstateAd;
    @Mock
    private RealEstateAdRequest realEstateAdRequest;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .country("US")
                .city("New York")
                .distreect("San")
                .street("123 Even House")
                .build();
        realEstateAdRequest = RealEstateAdRequest.builder()
                .yearBuilt(1999)
                .price(1000.0)
                .type("Modern")
                .aNumberOfBeds(1)
                .aNumberOfGarage(1)
                .aNumberOfBathrooms(1)
                .sqft(122)
                .address(address)
                .build();

        user = Users.builder()
                .email("user@example.com")
                .name("test")
                .phoneNumber("123")
                .password("test")
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
    void add_RealEstateAdRequest_ReturnsApiResponse() {
        // Given
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("user@example.com");

        when(userService.getUserByEmail("user@example.com")).thenReturn(user);
        when(realEstateAdRepo.save(any(RealEstateAd.class))).thenReturn(realEstateAd);
        when(addressRepo.save(any(Address.class))).thenReturn(address);

        // When
        ApiResponse response = underTest.add(principal, realEstateAdRequest);

        // Then
        assertNotNull(response);
        assertEquals("Property added", response.getMessage());
        assertTrue(response.isSuccess());

        verify(realEstateAdRepo).save(any(RealEstateAd.class));
        verify(addressRepo).save(any(Address.class));
        verify(userService).getUserByEmail("user@example.com");
    }


    @Test
    void getAll() {
        when(realEstateAdRepo.findAllByOrderByPublicationDateDesc()).thenReturn(List.of(realEstateAd));

        ApiResponse response = underTest.getAll();

        assertThat(response).isNotNull();
        assertThat(response.getData()).isInstanceOf(List.class);
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getMessage()).isEqualTo("List of real estate");
        verify(realEstateAdRepo).findAllByOrderByPublicationDateDesc();
    }

    @Test
    void test_RealEstateAdNotFoundException() {
        when(realEstateAdRepo.findAllByOrderByPublicationDateDesc()).thenReturn(List.of());

        assertThrows(CustomNotFoundException.class, () -> underTest.getAll());

        verify(realEstateAdRepo).findAllByOrderByPublicationDateDesc();
    }

}