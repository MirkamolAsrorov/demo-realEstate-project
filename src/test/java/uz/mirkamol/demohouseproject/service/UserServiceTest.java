package uz.mirkamol.demohouseproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.mirkamol.demohouseproject.model.Users;
import uz.mirkamol.demohouseproject.payload.ApiResponse;
import uz.mirkamol.demohouseproject.payload.UserRequest;
import uz.mirkamol.demohouseproject.repository.UserRepo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserRequest userRequest;
    private Users users;

    @BeforeEach
    void setUp() {
        userRequest = UserRequest.builder()
                .email("email@example.com")
                .password("password1234")
                .name("nameefs23")
                .phoneNumber("+998901234567")
                .build();
        users = Users.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .name(userRequest.getName())
                .phoneNumber(userRequest.getPhoneNumber())
                .build();
    }

    @Test
    void testAddUser_UserAlreadyExists() {
        // Arrange
        UserRequest userRequest = UserRequest.builder()
                .email("email@example.com")
                .password("password1234")
                .name("nameefs23")
                .phoneNumber("+998901234567")
                .build();

        when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(new Users()));

        // Act
        ApiResponse response = userService.addUser(userRequest);

        // Assert
        assertEquals("User already exsist, try to log in", response.getMessage());
        assertFalse(response.isSuccess());

    }

    @Test
    void testAddUser_Success() {
        // Given

        when(userRepo.findByEmail(anyString())).thenReturn(Optional.empty());

        // When
        ApiResponse response = userService.addUser(userRequest);

        // Then
        assertEquals("User added", response.getMessage());
        assertTrue(response.isSuccess());
        verify(userRepo).save(any(Users.class));
    }

    @Test
    void getUserByEmail() {
        userRepo.save(users);

        when(userRepo.findByEmail(userRequest.getEmail())).thenReturn(Optional.of(users));

        assertEquals(users, userService.getUserByEmail(userRequest.getEmail()));
        assertNotNull(userService.getUserByEmail("email@example.com"));
    }
}