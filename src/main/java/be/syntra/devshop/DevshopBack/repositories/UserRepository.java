package be.syntra.devshop.DevshopBack.repositories;

import be.syntra.devshop.DevshopBack.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
