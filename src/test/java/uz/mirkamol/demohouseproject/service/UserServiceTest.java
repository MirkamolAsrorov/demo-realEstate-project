package uz.mirkamol.demohouseproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.mirkamol.demohouseproject.exception.CustomNotFoundException;
import uz.mirkamol.demohouseproject.model.Users;
import uz.mirkamol.demohouseproject.payload.ApiResponse;
import uz.mirkamol.demohouseproject.payload.UserRequest;
import uz.mirkamol.demohouseproject.repository.UserRepo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService underTest;

    @Mock
    private UserRepo userRepo;


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
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(new Users()));

        // Act
        ApiResponse response = underTest.addUser(userRequest);

        // Assert
        assertEquals("User already exsist, try to log in", response.getMessage());
        assertFalse(response.isSuccess());

    }

    @Test
    void testAddUser_Success() {

        when(userRepo.findByEmail(anyString())).thenReturn(Optional.empty());

        ApiResponse response = underTest.addUser(userRequest);
        Users data = (Users) response.getData();

        assertEquals("User added", response.getMessage());
        assertTrue(response.isSuccess());
        verify(userRepo).save(data);
    }




    @Test
    void getUserByEmail() {
        userRepo.save(users);

        when(userRepo.findByEmail(userRequest.getEmail())).thenReturn(Optional.of(users));

        assertEquals(users, underTest.getUserByEmail(userRequest.getEmail()));
        assertNotNull(underTest.getUserByEmail("email@example.com"));
    }

    @Test
    void test_UserNotFoundException(){
        when(userRepo.findByEmail(userRequest.getEmail())).thenReturn(Optional.empty());

        assertThrows(CustomNotFoundException.class, () -> underTest.getUserByEmail(userRequest.getEmail()));

        verify(userRepo).findByEmail(userRequest.getEmail());
    }
}