package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{name}/cart")
    public ResponseEntity<CartDto> createArchivedCart(@RequestBody CartDto cartDto, @PathVariable String name) {
        cartService.saveCartToArchivedCarts(cartDto, name);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cartDto);
    }

}
