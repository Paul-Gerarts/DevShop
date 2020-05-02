package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.services.CartService;
import be.syntra.devshop.DevshopBack.services.utilities.CartMapperUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private CartMapperUtility cartMapperUtility;

    @Autowired
    public CartController(CartService cartService, CartMapperUtility cartMapperUtility) {
        this.cartService = cartService;
        this.cartMapperUtility = cartMapperUtility;
    }

    @PostMapping
    public ResponseEntity<CartDto> createArchivedCart(@RequestBody CartDto cartDto) {
        log.info("cart() -> {}", cartDto);
        Cart cart = cartMapperUtility.convertToCart(cartDto);
        cartService.saveCartToArchivedCarts(cart, cartDto.getUser());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cartDto);
    }

}
