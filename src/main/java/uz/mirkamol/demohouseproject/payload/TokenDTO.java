package uz.mirkamol.demohouseproject.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenDTO {
    private Long userId;
    private String accessToken;
}
