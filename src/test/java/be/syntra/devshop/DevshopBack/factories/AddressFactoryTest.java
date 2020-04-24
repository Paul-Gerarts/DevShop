package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.Address;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressFactoryTest {

    private final AddressFactory addressFactory = new AddressFactory();

    @Test
    void canBuildAddressTest() {
        // given
        String street = "street";
        String number = "11";
        String postBox = "B1";
        String postalCode = "3500";
        String city = "Genk";
        String country = "Belgium";

        // when
        Address addressResult = addressFactory.of(street, number, postBox, postalCode, city, country);

        // then
        assertThat(addressResult.getStreet()).isEqualTo(street);
        assertThat(addressResult.getNumber()).isEqualTo(number);
        assertThat(addressResult.getBoxNumber()).isEqualTo(postBox);
        assertThat(addressResult.getPostalCode()).isEqualTo(postalCode);
        assertThat(addressResult.getCity()).isEqualTo(city);
        assertThat(addressResult.getCountry()).isEqualTo(country);

    }
}
