package uz.mirkamol.demohouseproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String phoneNumber;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String role = "ROLE_USER";

}
