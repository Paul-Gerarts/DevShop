package be.syntra.devshop.DevshopBack.repositories;

import be.syntra.devshop.DevshopBack.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
