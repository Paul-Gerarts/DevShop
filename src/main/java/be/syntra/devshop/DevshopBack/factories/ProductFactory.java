package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.StarRating;
import be.syntra.devshop.DevshopBack.services.CategoryServiceImpl;
import be.syntra.devshop.DevshopBack.services.StarRatingServiceImpl;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
@NoArgsConstructor
public class ProductFactory {

    private CategoryServiceImpl categoryService;
    private StarRatingServiceImpl ratingService;

    @Autowired
    public ProductFactory(
            CategoryServiceImpl categoryService,
            StarRatingServiceImpl ratingService
    ) {
        this.categoryService = categoryService;
        this.ratingService = ratingService;
    }

    public Product of(
            String name,
            BigDecimal price,
            String description,
            boolean archived,
            List<Category> categories,
            List<StarRating> ratings
    ) {
        return Product.builder()
                .name(name)
                .price(price)
                .description(description)
                .archived(archived)
                .categories(categories)
                .ratings(ratings)
                .build();
    }

    public List<Product> ofRandomProducts(int amount) {
        List<Category> categories = categoryService.findAll();
        List<StarRating> ratings = ratingService.findAll();
        List<Product> products = new ArrayList<>();

        IntStream.range(1, amount).forEach(number -> {
            int randomCategory = new Random().nextInt(categories.size());
            Category category = categories.get(randomCategory);
            String categoryName = category.getName();
            String productName = categoryName + "-" + number;
            String description = "The MOST awesome " + productName + " you'll ever find!";
            products.add(of(
                    productName,
                    new BigDecimal(number),
                    description,
                    randomizeArchivedProductBy(randomCategory),
                    List.of(category),
                    ratings
            ));
        });

        return products;
    }

    private boolean randomizeArchivedProductBy(int randomNumber) {
        return randomNumber == 0;
    }

}
