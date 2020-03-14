package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public ResponseEntity<?> retrieveAllCarts() {
        return ResponseEntity
                .status(200)
                .body(cartService.findAll());
    }

    @PostMapping()
    public ResponseEntity<?> createCart(@RequestBody CartDto cartDto) {
        cartService.save(cartDto);
        return ResponseEntity
                .status(201)
                .body(cartDto);
    }

}
