package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.services.ShopOrderService;
import be.syntra.devshop.DevshopBack.services.utilities.ShopOrderMapper;
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

    private final ShopOrderService shopOrderService;
    private final ShopOrderMapper shopOrderMapper;

    @Autowired
    public CartController(ShopOrderService shopOrderService, ShopOrderMapper shopOrderMapper) {
        this.shopOrderService = shopOrderService;
        this.shopOrderMapper = shopOrderMapper;
    }

    @PostMapping
    public ResponseEntity<CartDto> createArchivedCart(@RequestBody CartDto cartDto) {
        log.info("cart() -> {}", cartDto);
        shopOrderService.saveShopOrder(shopOrderMapper.convertToShopOrder(cartDto), cartDto.getUser());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cartDto);
    }
}
