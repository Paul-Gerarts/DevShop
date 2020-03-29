package be.syntra.devshop.DevshopBack.security.services;

import be.syntra.devshop.DevshopBack.exceptions.UserNotFoundException;
import be.syntra.devshop.DevshopBack.security.entities.SecurityUser;
import be.syntra.devshop.DevshopBack.security.repositories.SecurityUserRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
@NoArgsConstructor
public class SecurityUserService implements UserDetailsService {

    private SecurityUserRepository userRepository;

    @Autowired
    public SecurityUserService(SecurityUserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public SecurityUser findByUserName(String userName) {
        Optional<SecurityUser> user = userRepository.findByUserName(userName);
        return user.orElseThrow(() -> new UserNotFoundException("This user cannot be found!"));
    }

    @Override
    public UserDetails loadUserByUsername(String s) {
        return findByUserName(s);
    }
}
