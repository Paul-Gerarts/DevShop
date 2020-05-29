package be.syntra.devshop.DevshopBack.repositories;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.StarRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Query("SELECT p FROM Product p "
            + "LEFT JOIN p.categories category "
            + "WHERE category.id = :categoryId "
            + "AND p.categories.size > 1"
    )
    List<Product> findAllWithCorrespondingCategories(
            @Param("categoryId") Long categoryId
    );

    @Query("SELECT sr FROM Product p "
            + "LEFT JOIN p.ratings sr "
            + "WHERE p.id = :productId"
    )
    Set<StarRating> findAllStarRatingFromProduct(
            @Param("productId") Long productId
    );

    @Query(value = "SELECT AVG(sr.RATING) FROM PRODUCT_STAR_RATING psr "
            + "LEFT JOIN STAR_RATING sr ON psr.STAR_RATING_ID = sr.STAR_RATING_ID "
            + "WHERE psr.PRODUCT_ID = :productId",
            nativeQuery = true
    )
    Optional<Double> getProductRating(
            @Param("productId") Long productId
    );

    @Query("SELECT p FROM Product p "
            + "LEFT JOIN p.categories category "
            + "WHERE UPPER(p.name) LIKE UPPER(CONCAT('%', COALESCE(:searchRequest, ''), '%')) "
            + "AND UPPER(p.description) LIKE UPPER(CONCAT('%', COALESCE(:description, ''), '%')) "
            + "AND p.price BETWEEN :priceLow AND :priceHigh "
            + "AND p.averageRating >= :averageRating "
            + "AND p.archived = :archived "
            + "AND ((category.name IN :selectedCategories) OR (:amountOfSelectedCategories = 0 AND category IN (SELECT c FROM Category c))) "
            + "GROUP BY p "
            + "HAVING SIZE(p.categories) >= :amountOfSelectedCategories"
    )
    Page<Product> findAllBySearchModel(
            Pageable pageable,
            @Param("searchRequest") String searchRequest,
            @Param("description") String description,
            @Param("priceLow") BigDecimal priceLow,
            @Param("priceHigh") BigDecimal priceHigh,
            @Param("averageRating") double averageRating,
            @Param("archived") boolean archived,
            @Param("selectedCategories") List<String> selectedCategories,
            @Param("amountOfSelectedCategories") int amountOfSelectedCategories
    );

    @Query(value = "SELECT ROUND(MAX(p.price)) AS roundValue FROM Product p "
            + "WHERE p.archived = :archived",
            nativeQuery = true
    )
    Double findRoundedMaxPrice(
            @Param("archived") boolean archived
    );

    Optional<Product> findById(Long id);

    Page<Product> findAllByArchived(boolean archived, Pageable pageable);
}
