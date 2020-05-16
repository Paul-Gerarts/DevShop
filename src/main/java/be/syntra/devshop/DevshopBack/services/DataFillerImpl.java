package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.StarRating;
import be.syntra.devshop.DevshopBack.factories.ProductFactory;
import be.syntra.devshop.DevshopBack.factories.SecurityUserFactory;
import be.syntra.devshop.DevshopBack.factories.UserFactory;
import be.syntra.devshop.DevshopBack.factories.UserRoleFactory;
import be.syntra.devshop.DevshopBack.repositories.CategoryRepository;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import be.syntra.devshop.DevshopBack.repositories.StarRatingRepository;
import be.syntra.devshop.DevshopBack.repositories.UserRepository;
import be.syntra.devshop.DevshopBack.security.entities.UserRole;
import be.syntra.devshop.DevshopBack.security.repositories.SecurityUserRepository;
import be.syntra.devshop.DevshopBack.security.repositories.UserRoleRepository;
import be.syntra.devshop.DevshopBack.security.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static be.syntra.devshop.DevshopBack.security.entities.UserRoles.ROLE_ADMIN;
import static be.syntra.devshop.DevshopBack.security.entities.UserRoles.ROLE_USER;

@Service
public class DataFillerImpl {

    @Value("${frontend.userName}")
    private String userName;
    @Value("${frontend.password}")
    private String password;
    private final ProductRepository productRepository;
    private final ProductFactory productFactory;
    private final UserRoleRepository userRoleRepository;
    private final UserRoleFactory userRoleFactory;
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final UserRoleService userRoleService;
    private final SecurityUserRepository securityUserRepository;
    private final SecurityUserFactory securityUserFactory;
    private final CategoryRepository categoryRepository;
    private final StarRatingRepository ratingRepository;

    @Autowired
    public DataFillerImpl(ProductRepository productRepository,
                          ProductFactory productFactory,
                          UserRoleRepository userRoleRepository,
                          UserRoleFactory userRoleFactory,
                          UserRepository userRepository,
                          UserFactory userFactory,
                          UserRoleService userRoleService,
                          SecurityUserRepository securityUserRepository,
                          SecurityUserFactory securityUserFactory,
                          CategoryRepository categoryRepository,
                          StarRatingRepository ratingRepository
    ) {
        this.productRepository = productRepository;
        this.productFactory = productFactory;
        this.userRoleRepository = userRoleRepository;
        this.userRoleFactory = userRoleFactory;
        this.userRepository = userRepository;
        this.userFactory = userFactory;
        this.userRoleService = userRoleService;
        this.securityUserRepository = securityUserRepository;
        this.securityUserFactory = securityUserFactory;
        this.categoryRepository = categoryRepository;
        this.ratingRepository = ratingRepository;
    }

    private UserRole retrieveAdminRole() {
        return userRoleService.findByRoleName(ROLE_ADMIN.name());
    }

    private List<Category> retrieveCategoryBy(String name) {
        return categoryRepository.findAllByName(name);
    }

    /*
     * Checks the database for values present
     * Fills the corresponding tables that are empty with values generated
     * All generated data is optional, except the one(s) marked with DO NOT DELETE
     */
    public void initialize() {

        /*
         * DO NOT DELETE - otherwise Spring Security won't know what roles there are -
         */
        if (userRoleRepository.count() == 0) {
            userRoleRepository.saveAll(List.of(
                    userRoleFactory.of(ROLE_USER.name()),
                    userRoleFactory.of(ROLE_ADMIN.name())
            ));
        }

        /*
         * DO NOT DELETE - otherwise Spring Security won't authorize frontend calls -
         */
        if (securityUserRepository.count() == 0) {
            securityUserRepository.save(
                    securityUserFactory.of(
                            userName,
                            new BCryptPasswordEncoder().encode(password),
                            Collections.singletonList(retrieveAdminRole())
                    ));
        }

        if (ratingRepository.count() == 0) {
            ratingRepository.saveAll(List.of(
                    StarRating.builder()
                            .rating(4)
                            .userName("lens.huygh@gmail.com")
                            .build(),
                    StarRating.builder()
                            .rating(1)
                            .userName("thomasf0n7a1n3@gmail.com")
                            .build(),
                    StarRating.builder()
                            .rating(4)
                            .userName("paul.gerarts@juvo.be")
                            .build()
            ));
        }

        if (categoryRepository.count() == 0) {
            categoryRepository.saveAll(List.of(
                    Category.builder()
                            .name("Accessories")
                            .build(),
                    Category.builder()
                            .name("Books")
                            .build(),
                    Category.builder()
                            .name("Desktops")
                            .build(),
                    Category.builder()
                            .name("Bags")
                            .build(),
                    Category.builder()
                            .name("Headphones")
                            .build(),
                    Category.builder()
                            .name("Office")
                            .build(),
                    Category.builder()
                            .name("Laptops")
                            .build()
            ));
        }

        if (productRepository.count() == 0) {
            productRepository.saveAll(getTestProducts());
        }

        if (userRepository.count() == 0) {
            userRepository.saveAll(List.of(
                    userFactory.ofSecurity(
                            "Lens",
                            "Huygh",
                            "lens.huygh@gmail.com"),
                    userFactory.ofSecurity(
                            "Thomas",
                            "Fontaine",
                            "thomasf0n7a1n3@gmail.com"),
                    userFactory.ofSecurity(
                            "Paul",
                            "Gerarts",
                            "paul.gerarts@juvo.be"),
                    userFactory.ofSecurity(
                            "User",
                            "McUserson",
                            "user@email.com"),
                    userFactory.ofSecurity(
                            "Admin",
                            "McAdminson",
                            "admin@emaill.com")
            ));
        }
    }

    private List<Product> getTestProducts() {
        List<Product> basicProducts = productFactory.ofRandomProducts(100);
        basicProducts.addAll(getCustomProducts());
        return basicProducts;
    }

    private List<Product> getCustomProducts() {
        return List.of(
                productFactory.of(
                        "keyboard",
                        new BigDecimal(150),
                        "The MOST fancy mechanical keyboard of all times",
                        false,
                        retrieveCategoryBy("Accessories"),
                        Collections.emptyList()),
                productFactory.of(
                        "mousepad",
                        new BigDecimal(3),
                        "The MOST non-waifu mousepad even your mom could approve",
                        false,
                        retrieveCategoryBy("Accessories"),
                        Collections.emptyList()),
                productFactory.of(
                        "gaming chair",
                        new BigDecimal("1600.99"),
                        "The MOST comfortable chair for your aching back",
                        false,
                        retrieveCategoryBy("Office"),
                        Collections.emptyList()),
                productFactory.of(
                        "headphones",
                        new BigDecimal(235),
                        "The MOST noise-cancelling headphones. You won't even hear your neighbours screaming",
                        false,
                        retrieveCategoryBy("Headphones"),
                        Collections.emptyList()),
                productFactory.of(
                        "Windows Pentium 3",
                        new BigDecimal(80),
                        "The MOST redundant PC that isn't even in production anymore",
                        true,
                        retrieveCategoryBy("Desktops"),
                        Collections.emptyList())
        );
    }
}
