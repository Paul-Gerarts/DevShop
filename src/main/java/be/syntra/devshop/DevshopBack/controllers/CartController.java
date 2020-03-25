package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/carts")
public class CartController {
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping()
    public ResponseEntity<?> createFinalizedCart(@RequestBody CartDto cartDto, @PathVariable Long userId) {
        cartService.saveFinalizedCart(cartDto, userId);
        return ResponseEntity
                .status(201)
                .body(cartDto);
    }

}
