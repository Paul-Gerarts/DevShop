package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Address;
import be.syntra.devshop.DevshopBack.models.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressMapperUtility {

    public Address convertToAddress(AddressDto addressDto) {
        return Address.builder()
                .street(addressDto.getStreet())
                .number(addressDto.getNumber())
                .boxNumber(addressDto.getBoxNumber())
                .postalCode(addressDto.getPostalCode())
                .city(addressDto.getCity())
                .country(addressDto.getCountry())
                .build();
    }

    public AddressDto convertToAddressDto(Address address) {
        return AddressDto.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .boxNumber(address.getBoxNumber())
                .postalCode(address.getPostalCode())
                .city(address.getCity())
                .country(address.getCountry())
                .build();
    }

}
