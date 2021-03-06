package be.syntra.devshop.DevshopBack.security.jwt;

import be.syntra.devshop.DevshopBack.security.entities.SecurityUser;
import be.syntra.devshop.DevshopBack.security.services.SecurityUserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JWTTokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String USER_ID = "userId";
    private final String base64Secret;
    private final Long tokenValidityInMilliseconds;
    private SecurityUserService securityUserService;
    public Key key;

    public JWTTokenProvider(
            @Value("${jwt.base64-secret}") String base64Secret,
            @Value("${jwt.token-validity-in-seconds}") Long tokenValidityInSeconds,
            SecurityUserService securityUserService
    ) {
        this.base64Secret = base64Secret;
        this.tokenValidityInMilliseconds = convertSecondsToMilliseconds(tokenValidityInSeconds);
        this.securityUserService = securityUserService;
    }

    private Long convertSecondsToMilliseconds(Long validityInSeconds) {
        return validityInSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        Long now = System.currentTimeMillis();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authentication.getAuthorities())
                .setHeaderParam(USER_ID, ((SecurityUser) authentication.getPrincipal()).getUserID())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        SecurityUser principal = securityUserService.findByUserName(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature ");
            log.trace("Invalid JWT signature trace: {} ", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token ");
            log.trace("Expired JWT token trace: {} ", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token ");
            log.trace("Unsupported JWT token trace: {} ", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid ");
            log.trace("JWT token compact of handler are invalid trace: {} ", e.getMessage());
        }
        return false;
    }

}
