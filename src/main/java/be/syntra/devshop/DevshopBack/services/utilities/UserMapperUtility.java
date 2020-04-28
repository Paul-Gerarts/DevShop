package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.text.WordUtils.capitalizeFully;

@Component
public class UserMapperUtility {

    @Autowired
    private AddressMapperUtility addressMapperUtility;

    @Autowired
    private CartMapperUtility cartMapperUtility;

    public UserDto convertToUserDto(User user) {
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(capitalizeFully(user.getFullName(), ' ', '-'))
                .address(addressMapperUtility.convertToAddressDto(user.getAddress()))
                .archivedCarts(cartMapperUtility.convertToCartDtoList(user.getArchivedCarts()))
                .build();
    }

    public User convertToUser(UserDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .fullName(capitalizeFully(userDto.getFullName(), ' ', '-'))
                .address(addressMapperUtility.convertToAddress(userDto.getAddress()))
                .archivedCarts(cartMapperUtility.convertToCartList(userDto.getArchivedCarts()))
                .build();
    }

}
