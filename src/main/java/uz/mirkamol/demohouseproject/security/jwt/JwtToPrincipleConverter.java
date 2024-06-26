package uz.mirkamol.demohouseproject.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import uz.mirkamol.demohouseproject.security.UserPrinciple;
import java.util.List;
@Component
@RequiredArgsConstructor
public class JwtToPrincipleConverter {

    public UserPrinciple convert(DecodedJWT jwt) {

        return UserPrinciple.builder()
            .userId(Long.valueOf(jwt.getSubject()))
            .email(jwt.getClaim("e").asString())
            .authorities(extractAuthoritiesFromClaim(jwt))
            .build();
    }

    public List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt){
        var claim = jwt.getClaim("e");

        if(claim.isNull() || claim.isMissing()) return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }



}
