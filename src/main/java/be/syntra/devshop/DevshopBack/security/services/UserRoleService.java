package be.syntra.devshop.DevshopBack.security.services;

import be.syntra.devshop.DevshopBack.exceptions.UserRoleNotFoundException;
import be.syntra.devshop.DevshopBack.security.entities.UserRole;
import be.syntra.devshop.DevshopBack.security.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserRole findByRoleName(String name) {
        return userRoleRepository.findUserRoleByName(name).orElseThrow(
                () -> new UserRoleNotFoundException("This userRole: " + name + " cannot be found ")
        );
    }

    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }
}
