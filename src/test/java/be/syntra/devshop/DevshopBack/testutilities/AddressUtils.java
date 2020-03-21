package be.syntra.devshop.DevshopBack.testutilities;

import be.syntra.devshop.DevshopBack.entities.Address;
import be.syntra.devshop.DevshopBack.models.AddressDto;

public class AddressUtils {
    public static Address createAddress() {
        return Address.builder()
                .street("somewhere street")
                .number("1")
                .boxNumber("")
                .postalCode("1234")
                .city("Somewhere")
                .country("Belgium")
                .build();
    }

    public static AddressDto createAddressDto() {
        return AddressDto.builder()
                .street("somewhere street")
                .number("1")
                .boxNumber("")
                .postalCode("1234")
                .city("Somewhere")
                .country("Belgium")
                .build();
    }

    public static Address createAddressWithId() {
        return Address.builder()
                .id(1L)
                .street("somewhere street")
                .number("1")
                .boxNumber("")
                .postalCode("1234")
                .city("Somewhere")
                .country("Belgium")
                .build();

    }
}
