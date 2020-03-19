package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Address;
import be.syntra.devshop.DevshopBack.models.AddressDto;
import be.syntra.devshop.DevshopBack.testutilities.AddressUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressMapperUtilityTest {
    private AddressMapperUtility addressMapperUtility = new AddressMapperUtility();

    @Test
    void convertToAddress() {
        // given
        Address address = AddressUtils.createAddress();
        AddressDto addressDto = AddressUtils.createAddressDto();

        // when
        Address mappedAddress = AddressMapperUtility.convertToAddress(addressDto);

        // then
        assertEquals(mappedAddress.getClass(), Address.class);
        assertEquals(mappedAddress.getStreet(), address.getStreet());
        assertEquals(mappedAddress.getNumber(), address.getNumber());
        assertEquals(mappedAddress.getBoxNumber(), address.getBoxNumber());
        assertEquals(mappedAddress.getPostalCode(), address.getPostalCode());
        assertEquals(mappedAddress.getCity(), address.getCity());
        assertEquals(mappedAddress.getProvince(), address.getProvince());
        assertEquals(mappedAddress.getCountry(), address.getCountry());

    }

    @Test
    void convertToAddressDto() {
        // given
        Address address = AddressUtils.createAddressWithId();
        AddressDto addressDto = AddressUtils.createAddressDto();

        // when
        AddressDto mappedAddressDto = AddressMapperUtility.convertToAddressDto(address);

        // then
        assertEquals(mappedAddressDto.getClass(), AddressDto.class);
        assertEquals(mappedAddressDto.getStreet(), addressDto.getStreet());
        assertEquals(mappedAddressDto.getNumber(), addressDto.getNumber());
        assertEquals(mappedAddressDto.getBoxNumber(), addressDto.getBoxNumber());
        assertEquals(mappedAddressDto.getPostalCode(), addressDto.getPostalCode());
        assertEquals(mappedAddressDto.getCity(), addressDto.getCity());
        assertEquals(mappedAddressDto.getProvince(), addressDto.getProvince());
        assertEquals(mappedAddressDto.getCountry(), addressDto.getCountry());

    }
}
