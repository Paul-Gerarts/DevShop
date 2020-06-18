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
    private ShopOrderMapper shopOrderMapper;

    public User convertToUser(UserDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .fullName(capitalizeFully(userDto.getFullName(), ' ', '-'))
                .address(addressMapper.convertToAddress(userDto.getAddress()))
                .shopOrders(shopOrderMapper.convertToCartList(userDto.getArchivedCarts()))
                .build();
    }

}
