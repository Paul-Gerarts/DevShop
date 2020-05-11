package be.syntra.devshop.DevshopBack.repositories;

import be.syntra.devshop.DevshopBack.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p "
            + "LEFT JOIN p.categories category "
            + "WHERE category.id = :categoryId "
            + "AND p.categories.size = 1"
    )
    List<Product> findAllWithCorrespondingCategory(
            @Param("categoryId") Long categoryId
    );

    List<Product> findAllByArchivedFalse();

    List<Product> findAllByArchivedTrue();

    List<Product> findAllByNameContainingIgnoreCaseAndArchivedFalse(String searchRequest);

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String productName);
}
