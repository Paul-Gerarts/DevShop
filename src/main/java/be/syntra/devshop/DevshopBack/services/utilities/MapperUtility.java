package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Address;
import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.AddressDto;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.models.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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
                .password(userDto.getPassword())
                .address(convertToAddress(userDto.getAddress()))
                .activeCart(convertToCart(userDto.getActiveCart()))
                .archivedCarts(convertToCartList(userDto.getArchivedCarts()))
                .build();
    }

    public UserDto convertToUserDto(User user) {
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .password(user.getPassword())
                .address(convertToAddressDto(user.getAddress()))
                .activeCart(convertToCartDto(user.getActiveCart()))
                .archivedCarts(convertToCartDtoList(user.getArchivedCarts()))
                .build();
    }

    public AddressDto convertToAddressDto(Address address) {
        return AddressDto.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .boxNumber(address.getBoxNumber())
                .postalCode(address.getPostalCode())
                .city(address.getCity())
                .province(address.getProvince())
                .country(address.getCountry())
                .build();
    }

    public Address convertToAddress(AddressDto addressDto) {
        return Address.builder()
                .street(addressDto.getStreet())
                .number(addressDto.getNumber())
                .boxNumber(addressDto.getBoxNumber())
                .postalCode(addressDto.getPostalCode())
                .city(addressDto.getCity())
                .province(addressDto.getProvince())
                .country(addressDto.getCountry())
                .build();
    }

    public CartDto convertToCartDto(Cart cart) {
        return CartDto.builder()
                .cartCreationDateTime(cart.getCartCreationDateTime())
                .products(convertToProductDtoList(cart.getProducts()))
                .activeCart(cart.isActiveCart())
                .finalizedCart(cart.isFinalizedCart())
                .paidCart(cart.isPaidCart())
                .build();
    }


    public Cart convertToCart(CartDto cartDto) {
        return Cart.builder()
                .cartCreationDateTime(cartDto.getCartCreationDateTime())
                .products(convertToProductList(cartDto.getProducts()))
                .activeCart(cartDto.isActiveCart())
                .finalizedCart(cartDto.isFinalizedCart())
                .paidCart(cartDto.isPaidCart())
                .build();
    }

    public List<Product> convertToProductList(List<ProductDto> productDtoList) {
        return productDtoList.stream().map(this::convertToProduct).collect(Collectors.toList());
    }

    public List<CartDto> convertToCartDtoList(List<Cart> carts) {
        return carts.stream().map(this::convertToCartDto).collect(Collectors.toList());
    }

    public List<Cart> convertToCartList(List<CartDto> cartDtoList) {
        return cartDtoList.stream().map(this::convertToCart).collect(Collectors.toList());
    }

    public List<ProductDto> convertToProductDtoList(List<Product> products) {
        return products.stream().map(this::convertToProductDto).collect(Collectors.toList());
    }

}
