package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.models.UserDto;
import org.springframework.stereotype.Service;


@Service
public class MapperUtility {

    public Product convertToProduct(ProductDto productDTO) {
        return Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .build();
    }

    public ProductDto convertToProductDto(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    public User convertToUser(UserDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .fullName(userDto.getFullName())
                .address(userDto.getAddress())
                .activeCart(userDto.getActiveCart())
                .build();
    }

    public UserDto convertToUserDto(User user) {
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .address(user.getAddress())
                .activeCart(user.getActiveCart())
                .build();
    }

}
