package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Address;
import be.syntra.devshop.DevshopBack.models.AddressDto;
import be.syntra.devshop.DevshopBack.testutilities.AddressUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressMapperTest {

    private final AddressMapper addressMapper = new AddressMapper();

    @Test
    void convertToAddress() {
        // given
        AddressDto addressDto = AddressUtils.createAddressDto();

        // when
        Address mappedAddress = addressMapper.convertToAddress(addressDto);

        // then
        assertEquals(mappedAddress.getClass(), Address.class);
        assertEquals(mappedAddress.getStreet(), addressDto.getStreet());
        assertEquals(mappedAddress.getNumber(), addressDto.getNumber());
        assertEquals(mappedAddress.getBoxNumber(), addressDto.getBoxNumber());
        assertEquals(mappedAddress.getPostalCode(), addressDto.getPostalCode());
        assertEquals(mappedAddress.getCity(), addressDto.getCity());
        assertEquals(mappedAddress.getCountry(), addressDto.getCountry());

    }

    @Test
    void convertToAddressDto() {
        // given
        Address address = AddressUtils.createAddressWithId();

        // when
        AddressDto mappedAddressDto = addressMapper.convertToAddressDto(address);

        // then
        assertEquals(mappedAddressDto.getClass(), AddressDto.class);
        assertEquals(mappedAddressDto.getStreet(), address.getStreet());
        assertEquals(mappedAddressDto.getNumber(), address.getNumber());
        assertEquals(mappedAddressDto.getBoxNumber(), address.getBoxNumber());
        assertEquals(mappedAddressDto.getPostalCode(), address.getPostalCode());
        assertEquals(mappedAddressDto.getCity(), address.getCity());
        assertEquals(mappedAddressDto.getCountry(), address.getCountry());

    }
}
