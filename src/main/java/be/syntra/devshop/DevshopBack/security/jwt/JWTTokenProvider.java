package be.syntra.devshop.DevshopBack.security.jwt;

import be.syntra.devshop.DevshopBack.security.models.CustomUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JWTTokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String USER_ID = "userId";
    private final String base64Secret;
    private final Long tokenValidityInMilliseconds;
    public Key key;

    public JWTTokenProvider(
            @Value("${jwt.base64-secret}") String base64Secret,
            @Value("${jwt.token-validity-in-seconds}") Long tokenValidityInSeconds) {
        this.base64Secret = base64Secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1200;
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
                .setHeaderParam(USER_ID, ((CustomUser) authentication.getPrincipal()).getUserID())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableList());
        Long gebruikerId = null != claims.get(USER_ID)
                ? (Long) claims.get(USER_ID)
                : null;
        CustomUser principal = new CustomUser(claims.getSubject(), "", authorities, gebruikerId);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature ");
            log.trace("Invalid JWT signature trace: {} ", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token ");
            log.trace("Expired JWT token trace: {} ", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token ");
            log.trace("Unsupported JWT token trace: {} ", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid ");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

}
