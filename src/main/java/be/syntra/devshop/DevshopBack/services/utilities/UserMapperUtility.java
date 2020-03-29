package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;

import static be.syntra.devshop.DevshopBack.services.utilities.AddressMapperUtility.convertToAddress;
import static be.syntra.devshop.DevshopBack.services.utilities.AddressMapperUtility.convertToAddressDto;
import static be.syntra.devshop.DevshopBack.services.utilities.CartMapperUtility.*;

public class UserMapperUtility {
    public static UserDto convertToUserDto(User user) {
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .address(convertToAddressDto(user.getAddress()))
                .activeCart(convertToCartDto(user.getActiveCart()))
                .archivedCarts(convertToCartDtoList(user.getArchivedCarts()))
                .build();
    }

    public static User convertToUser(UserDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .fullName(userDto.getFullName())
                .address(convertToAddress(userDto.getAddress()))
                .activeCart(convertToCart(userDto.getActiveCart()))
                .archivedCarts(convertToCartList(userDto.getArchivedCarts()))
                .build();
    }

}
