package be.syntra.devshop.DevshopBack.security.repositories;

import be.syntra.devshop.DevshopBack.security.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

   Optional<UserRole> findUserRoleByName(String name);

}
