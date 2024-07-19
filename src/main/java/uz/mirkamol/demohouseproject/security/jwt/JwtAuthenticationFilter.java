package uz.mirkamol.demohouseproject.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.mirkamol.demohouseproject.security.UserPrinciple;
import uz.mirkamol.demohouseproject.security.UserPrincipleAuthenticationToken;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipleConverter jwtToPrincipleConverter;
    private final JwtHelper jwtHelper;
    private Authentication authentication;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = extractTokenFromRequest(request);
        if (token.isPresent()) {
            UserPrinciple userPrinciple =
                    jwtToPrincipleConverter.convert(
                            jwtDecoder.decodeAccessToken(
                                    token.get()));

                Authentication authentication = new UserPrincipleAuthenticationToken(userPrinciple);
            if (jwtHelper.isTokenValid(token.get(), userPrinciple)) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        filterChain.doFilter(request, response);

    }


    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }

}
