package uz.mirkamol.demohouseproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.mirkamol.demohouseproject.payload.ApiResponse;
import uz.mirkamol.demohouseproject.repository.UserRepo;
import uz.mirkamol.demohouseproject.security.UserPrinciple;
import uz.mirkamol.demohouseproject.security.jwt.JwtIssuer;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;


    public ApiResponse attemptLogin(String username, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrinciple) authentication.getPrincipal();
        var roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();


        var token = jwtIssuer.issue(
                principal.getUserId(),
                principal.getEmail(),
                roles);

        return ApiResponse.builder()
                .message("You successfully logged in")
                .success(true)
                .accessToken(token)
                .build();
    }

}