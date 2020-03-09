package be.syntra.devshop.DevshopBack.security.services;

import be.syntra.devshop.DevshopBack.entities.UserRole;
import be.syntra.devshop.DevshopBack.security.exceptions.UserRoleNotFoundException;
import be.syntra.devshop.DevshopBack.security.repositories.UserRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    private UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserRole findByRolName(String name) throws UserRoleNotFoundException {
        return userRoleRepository.findUserRoleByName(name).orElseThrow(
                () -> new UserRoleNotFoundException("This userRole: " + name + " cannot be found ")
        );
    }
}
