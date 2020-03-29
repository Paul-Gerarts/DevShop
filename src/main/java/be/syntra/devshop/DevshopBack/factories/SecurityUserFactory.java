package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.security.entities.SecurityUser;
import be.syntra.devshop.DevshopBack.security.entities.UserRole;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SecurityUserFactory {

    public SecurityUser of(
            String userName,
            String password,
            List<UserRole> userRoles
    ) {
        return SecurityUser.builder()
                .userName(userName)
                .password(password)
                .userRoles(userRoles)
                .build();
    }
}
