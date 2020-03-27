package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.security.entities.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserRoleFactory {

    public UserRole of(
            String userRole
    ) {
        return UserRole.builder()
                .name(userRole)
                .build();
    }
}
