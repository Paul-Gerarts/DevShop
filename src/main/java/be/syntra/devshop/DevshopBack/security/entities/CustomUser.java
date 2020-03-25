package be.syntra.devshop.DevshopBack.security.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CustomUser extends User {

    private Long userID;

    /*
     * This is a custom Object upon which we set our actual security
     * it seperates our User business logic from the little that's needed for security
     * when changing userDetails, one should use the @AuthenticaionPrincipal CustomUser userDetails
     *@See https://github.com/spring-projects/spring-security/blob/aaa9708b952409269f2829e60126344a1288019e/web/src/main/java/org/springframework/security/web/method/annotation/AuthenticationPrincipalArgumentResolver.java#L116-L121
     */
    public CustomUser(String username,
                      String password,
                      Collection<? extends GrantedAuthority> authorities,
                      Long userID) {
        super(username, password, authorities);
        this.userID = userID;
    }
}
