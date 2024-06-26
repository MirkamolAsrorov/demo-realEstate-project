package uz.mirkamol.demohouseproject.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
@Configuration
public class JwtProperties {

   @Value("${security.jwt.secret-key}")
   private String secretKey;
}
