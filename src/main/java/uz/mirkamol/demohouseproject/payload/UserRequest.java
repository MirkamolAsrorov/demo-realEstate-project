package uz.mirkamol.demohouseproject.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @Size(min = 3, max = 20)
    private String name;
    @Pattern(regexp = "^[0-9]{12}$", message = "User Phone number must be a 9-digit number.")
    private String phoneNumber;
    @Email
    private String email;
    @Size(min = 8, max = 20)
    private String password;
}