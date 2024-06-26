package uz.mirkamol.demohouseproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.mirkamol.demohouseproject.payload.ApiResponse;
import uz.mirkamol.demohouseproject.payload.LoginRequest;
import uz.mirkamol.demohouseproject.payload.UserRequest;
import uz.mirkamol.demohouseproject.service.AuthService;
import uz.mirkamol.demohouseproject.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<ApiResponse> addUser(@Validated @RequestBody UserRequest userRequest) {
        var apiResponse = userService.addUser(userRequest);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@Validated@RequestBody LoginRequest loginRequest) {
        var apiResponse = authService.attemptLogin(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(apiResponse);
    }




}
