package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.security.controllers.dtos.RegisterDto;
import org.springframework.stereotype.Component;

import static org.apache.commons.text.WordUtils.capitalizeFully;

@Component
public class UserFactory {

    private AddressFactory addressFactory = new AddressFactory();

    public User ofSecurity(
            String firstName,
            String lastName,
            String email
    ) {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .fullName(capitalizeFully(firstName + " " + lastName, ' ', '-'))
                .email(email)
                .build();
    }

    public User ofRegisterDto(RegisterDto registerDto) {
        return User.builder()
                .email(registerDto.getUserName())
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .fullName(capitalizeFully(registerDto.getFirstName() + " " + registerDto.getLastName(), ' ', '-'))
                .address(addressFactory.of(
                        registerDto.getStreet(),
                        registerDto.getNumber(),
                        registerDto.getBoxNumber(),
                        registerDto.getPostalCode(),
                        registerDto.getCity(),
                        registerDto.getCountry()))
                .build();
    }
}
