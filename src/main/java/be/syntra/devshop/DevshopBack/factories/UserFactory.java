package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.security.controllers.dtos.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserFactory {

    @Autowired
    private AddressFactory addressFactory;

    @Autowired
    private CartFactory cartFactory;

    public User ofSecurity(
            String firstName,
            String lastName,
            String email
    ) {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .fullName(firstName + " " + lastName)
                .email(email)
                .build();
    }

    public User ofRegisterDto(RegisterDto registerDto) {
        return User.builder()
                .email(registerDto.getUserName())
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .fullName(registerDto.getFirstName() + " " + registerDto.getLastName())
                .archivedCarts(new ArrayList<>())
                .address(addressFactory.of(
                        registerDto.getStreet(),
                        registerDto.getNumber(),
                        registerDto.getBoxNumber(),
                        registerDto.getPostalCode(),
                        registerDto.getCity(),
                        registerDto.getCountry()))
                .activeCart(cartFactory.ofEmptyCart())
                .build();
    }
}
