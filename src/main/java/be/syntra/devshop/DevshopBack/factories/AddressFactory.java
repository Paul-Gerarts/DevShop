package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressFactory {

    public Address of(
            String street,
            String number,
            String boxNumber,
            String postalCode,
            String city,
            String country
    ) {
        return Address.builder()
                .street(street)
                .number(number)
                .boxNumber(boxNumber)
                .postalCode(postalCode)
                .city(city)
                .country(country)
                .build();
    }
}
