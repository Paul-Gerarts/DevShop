package be.syntra.devshop.DevshopBack.repositories;

import be.syntra.devshop.DevshopBack.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "userRoleEntityList")
    Optional<User> findOneWithUserRoleByEmailIgnoreCase(String email);

    Optional<User> findOneByEmailIgnoreCase(String email);

    List<User> findAllByEmailNot(String username);
}
