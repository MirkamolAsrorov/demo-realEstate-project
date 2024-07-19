package uz.mirkamol.demohouseproject.payload;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse {
    private String message;
    private boolean success;
    private Object data;
    private String accessToken;


}
