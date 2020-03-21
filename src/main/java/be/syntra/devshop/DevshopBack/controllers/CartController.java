package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class CartController {
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping()
    public ResponseEntity<?> createCart(@RequestBody CartDto cartDto) {
        cartService.save(cartDto);
        return ResponseEntity
                .status(201)
                .body(cartDto);
    }

}
