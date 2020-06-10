package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.text.WordUtils.capitalizeFully;

@Component
public class UserMapper {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private CartMapper cartMapper;

    public UserDto convertToUserDto(User user) {
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(capitalizeFully(user.getFullName(), ' ', '-'))
                .address(addressMapper.convertToAddressDto(user.getAddress()))
                .archivedCarts(cartMapper.convertToCartDtoList(user.getArchivedShopOrders()))
                .build();
    }

    public User convertToUser(UserDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .fullName(capitalizeFully(userDto.getFullName(), ' ', '-'))
                .address(addressMapper.convertToAddress(userDto.getAddress()))
                .archivedShopOrders(cartMapper.convertToCartList(userDto.getArchivedCarts()))
                .build();
    }

}
