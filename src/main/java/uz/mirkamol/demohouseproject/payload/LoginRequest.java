package uz.mirkamol.demohouseproject.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {
    @Email
    private final String email;
    @Size(min = 8, max = 20)
    private final String password;
}
