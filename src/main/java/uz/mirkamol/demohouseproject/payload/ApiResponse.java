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

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
    public ApiResponse(String message, boolean success, String accessToken) {
        this.message = message;
        this.success = success;
        this.accessToken = accessToken;
    }
}
