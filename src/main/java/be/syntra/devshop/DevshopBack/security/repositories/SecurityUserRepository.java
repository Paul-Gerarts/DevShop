package be.syntra.devshop.DevshopBack.security.repositories;

import be.syntra.devshop.DevshopBack.security.entities.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityUserRepository extends JpaRepository<SecurityUser, Long> {

    Optional<SecurityUser> findByUserName(String userName);
}
