package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.*;
import be.syntra.devshop.DevshopBack.services.CategoryService;
import be.syntra.devshop.DevshopBack.services.ProductService;
import be.syntra.devshop.DevshopBack.services.SearchService;
import be.syntra.devshop.DevshopBack.services.StarRatingService;
import be.syntra.devshop.DevshopBack.services.utilities.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final SearchService searchService;
    private final StarRatingService ratingService;
    private final SearchModelMapper searchModelMapper;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final StarRatingMapper starRatingMapper;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ProductController(
            ProductService productService,
            CategoryService categoryService,
            SearchService searchService,
            StarRatingService ratingService,
            SearchModelMapper searchModelMapper,
            ProductMapper productMapper,
            CategoryMapper categoryMapper,
            StarRatingMapper starRatingMapper,
            ReviewMapper reviewMapper
    ) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.searchService = searchService;
        this.ratingService = ratingService;
        this.searchModelMapper = searchModelMapper;
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
        this.starRatingMapper = starRatingMapper;
        this.reviewMapper = reviewMapper;
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<ProductList> retrieveAllWithCorrespondingCategory(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productMapper.convertToProductListObject(productService.findAllByCorrespondingCategory(id)));
    }

    /*
     *@Returns: 201-created code when our product's successfully saved
     */
    @PostMapping()
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        saveProduct(productDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productDto);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        product.setAverageRating(productService.getProductRating(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(product);
    }

    @GetMapping("{userName}/ratings/{id}")
    public ResponseEntity<StarRatingDto> findBy(@PathVariable String userName, @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(starRatingMapper.mapToDto(ratingService.getRatingFromUser(id, userName)));
    }

    @GetMapping("/ratings/{id}")
    public ResponseEntity<StarRatingSet> findBy(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(starRatingMapper.mapToStarRatingSet(productService.getAllRatingsFromProduct(id)));
    }

    @PostMapping("/ratings")
    public ResponseEntity<StarRatingDto> submitRating(@RequestBody StarRatingDto starRatingDto) {
        productService.submitRating(starRatingMapper.mapToStarRating(starRatingDto), starRatingDto.getProductId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(starRatingDto);
    }

    @PostMapping("/update")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        saveProduct(productDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productDto);
    }

    @GetMapping("/categories")
    public ResponseEntity<CategoryList> retrieveAllCategories() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryMapper.convertToCategoryList(categoryService.findAll()));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> findCategoryBy(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryMapper.mapToCategoryDto(categoryService.findById(id)));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        productService.removeOneCategory(id);
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/categories/set_category")
    public ResponseEntity<CategoryChangeDto> setNewCategoryForProducts(@RequestBody CategoryChangeDto categoryChangeDto) {
        productService.setNewCategory(categoryChangeDto.getCategoryToDelete(), categoryChangeDto.getCategoryToSet());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryChangeDto);
    }

    @PostMapping("/categories/update_category")
    public ResponseEntity<CategoryChangeDto> updateCategory(@RequestBody CategoryChangeDto categoryChangeDto) {
        categoryService.updateCategory(categoryChangeDto.getNewCategoryName(), categoryChangeDto.getCategoryToSet());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryChangeDto);
    }

    @PostMapping("/searching")
    public ResponseEntity<ProductList> retrieveAllProductsBySearchModel(@RequestBody SearchModelDto searchModelDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productMapper.convertToProductListObject(
                        searchService.applySearchModel(
                                searchModelMapper.convertToSearchModel(searchModelDto))));
    }

    private void saveProduct(ProductDto productDto) {
        productService.save(productMapper.convertToProduct(productDto));
    }

    @PostMapping("/reviews")
    public ResponseEntity<ReviewDto> submitReview(@RequestBody ReviewDto reviewDto) {
        log.info("submitReview() -> {}", reviewDto.getReviewText());
        productService.submitReview(reviewMapper.mapToReview(reviewDto), reviewDto.getProductId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewDto);
    }

    @PutMapping("/reviews")
    public ResponseEntity<ReviewDto> updateReview(@RequestBody ReviewDto reviewDto) {
        log.info("updateReview() -> {}", reviewDto.getReviewText());
        productService.updateReview(reviewMapper.mapToReview(reviewDto), reviewDto.getProductId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewDto);
    }

    @DeleteMapping("/reviews")
    public ResponseEntity<ReviewDto> removeReview(@RequestBody ReviewDto reviewDto) {
        log.info("removeReview() -> {}", reviewDto.getReviewText());
        productService.removeReview(reviewMapper.mapToReview(reviewDto), reviewDto.getProductId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewDto);
    }
}
