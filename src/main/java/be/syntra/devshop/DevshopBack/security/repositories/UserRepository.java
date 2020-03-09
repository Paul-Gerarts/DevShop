package be.syntra.devshop.DevshopBack.security.repositories;

import be.syntra.devshop.DevshopBack.entities.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Customer, Long> {

    @EntityGraph(attributePaths = "userRoleEntityList")
    Optional<Customer> findOneWithUserRoleByEmailIgnoreCase(String email);

    Optional<Customer> findOneByEmailIgnoreCase(String email);

    List<Customer> findAllByEmailNot(String username);
}
