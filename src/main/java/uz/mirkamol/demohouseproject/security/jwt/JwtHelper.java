package uz.mirkamol.demohouseproject.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtHelper {
    private final JwtDecoder jwtDecoder;

    public String extractUsername(DecodedJWT decodedJWT) {
        var email = decodedJWT.getClaim("e");
        return email.asString();
    }

//weak and not secure
    public boolean isTokenValid(String token, UserDetails userDetails) {
        DecodedJWT decodedJWT = jwtDecoder.decodeAccessToken(token);
        final String username = extractUsername(decodedJWT);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        DecodedJWT decodedJWT = jwtDecoder.decodeAccessToken(token);
        return decodedJWT.getExpiresAt();
    }

}
