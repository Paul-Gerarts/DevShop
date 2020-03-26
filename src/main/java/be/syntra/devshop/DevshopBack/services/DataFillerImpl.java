package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.factories.ProductFactory;
import be.syntra.devshop.DevshopBack.factories.UserFactory;
import be.syntra.devshop.DevshopBack.factories.UserRoleFactory;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import be.syntra.devshop.DevshopBack.repositories.UserRepository;
import be.syntra.devshop.DevshopBack.security.entities.UserRole;
import be.syntra.devshop.DevshopBack.security.repositories.UserRoleRepository;
import be.syntra.devshop.DevshopBack.security.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static be.syntra.devshop.DevshopBack.security.entities.UserRoles.ROLE_ADMIN;
import static be.syntra.devshop.DevshopBack.security.entities.UserRoles.ROLE_USER;

@Service
public class DataFillerImpl {

    private ProductRepository productRepository;
    private ProductFactory productFactory;
    private UserRoleRepository userRoleRepository;
    private UserRoleFactory userRoleFactory;
    private UserRepository userRepository;
    private UserFactory userFactory;
    private UserRoleService userRoleService;

    @Autowired
    public DataFillerImpl(ProductRepository productRepository,
                          ProductFactory productFactory,
                          UserRoleRepository userRoleRepository,
                          UserRoleFactory userRoleFactory,
                          UserRepository userRepository,
                          UserFactory userFactory,
                          UserRoleService userRoleService
    ) {
        this.productRepository = productRepository;
        this.productFactory = productFactory;
        this.userRoleRepository = userRoleRepository;
        this.userRoleFactory = userRoleFactory;
        this.userRepository = userRepository;
        this.userFactory = userFactory;
        this.userRoleService = userRoleService;
    }

    private UserRole retrieveUserRole() {
        return userRoleService.findByRoleName(ROLE_USER.name());
    }

    private UserRole retrieveAdminRole() {
        return userRoleService.findByRoleName(ROLE_ADMIN.name());
    }

    /*
     * Checks the database for values present
     * Fills the corresponding tables that are empty with values generated
     * All generated data is optional, except the one(s) marked with DO NOT DELETE
     */
    public void initialize() {
        if (productRepository.count() == 0) {
            productRepository.saveAll(List.of(
                    productFactory.of(
                            "keyboard",
                            new BigDecimal(150)),
                    productFactory.of(
                            "mousepad",
                            new BigDecimal(3))
            ));
        }

        /*
         * DO NOT DELETE - otherwise Spring Security won't know what roles there are -
         */
        if (userRoleRepository.count() == 0) {
            userRoleRepository.saveAll(List.of(
                    userRoleFactory.of(ROLE_USER.name()),
                    userRoleFactory.of(ROLE_ADMIN.name())
            ));
        }

        if (userRepository.count() == 0) {
            userRepository.saveAll(List.of(
                    userFactory.ofSecurity(
                            "Lens",
                            "Lens",
                            List.of(retrieveUserRole(), retrieveAdminRole()),
                            "Huygh",
                            "lens.huygh@gmail.com"),
                    userFactory.ofSecurity(
                            "Thomas",
                            "Thomas",
                            List.of(retrieveUserRole(), retrieveAdminRole()),
                            "Fontaine",
                            "thomasf0n7a1n3@gmail.com"),
                    userFactory.ofSecurity(
                            "Paul",
                            "Paul",
                            List.of(retrieveUserRole(), retrieveAdminRole()),
                            "Gerarts",
                            "paul.gerarts@juvo.be")
            ));
        }
    }
}
