package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.security.entities.UserRole;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFactory {

    public User ofSecurity(
            String firstName,
            String password,
            List<UserRole> authorities,
            String lastName
    ) {
        return User.builder()
                .firstName(firstName)
                .password(password)
                .userRoles(authorities)
                .lastName(lastName)
                .fullName(firstName + " " + lastName)
                .build();
    }
}
