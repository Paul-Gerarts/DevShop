package be.syntra.devshop.DevshopBack.repositories;

import be.syntra.devshop.DevshopBack.entities.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<ShopOrder, Long> {

}
