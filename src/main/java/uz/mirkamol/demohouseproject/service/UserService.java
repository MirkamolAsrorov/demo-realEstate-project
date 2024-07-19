package uz.mirkamol.demohouseproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mirkamol.demohouseproject.exception.CustomNotFoundException;
import uz.mirkamol.demohouseproject.model.Users;
import uz.mirkamol.demohouseproject.payload.ApiResponse;
import uz.mirkamol.demohouseproject.payload.UserRequest;
import uz.mirkamol.demohouseproject.repository.UserRepo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse addUser(UserRequest userRequest) {
        if (checkIfUserExsists(userRequest)){
            return ApiResponse.builder()
                    .message("User already exsist, try to log in")
                    .success(false)
                    .build();
        }

        var user = new Users();
        user.setName(userRequest.getName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(
                userRequest.getPassword())
        );

        userRepo.save(user);
        return ApiResponse.builder()
                .message("User added")
                .success(true)
                .data(user)
                .build();
    }

    public Users getUserByEmail(String email) {
        Optional<Users> byEmail = userRepo.findByEmail(email);
        if (byEmail.isEmpty()) {

            throw new CustomNotFoundException("User not found");
        }

        return byEmail.get();
    }

    public boolean checkIfUserExsists(UserRequest userRequest){
        String email = userRequest.getEmail();

        return userRepo.findByEmail(email).isPresent();
    }
}
