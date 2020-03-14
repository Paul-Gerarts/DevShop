package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.models.AddressDto;
import be.syntra.devshop.DevshopBack.services.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/addresses")
public class AddressController {
    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping()
    public ResponseEntity<?> retrieveAllAddresses() {
        return ResponseEntity
                .status(200)
                .body(addressService.findAll());
    }

    @PostMapping()
    public ResponseEntity<?> createAddress(AddressDto addressDto) {
        addressService.save(addressDto);
        return ResponseEntity
                .status(201)
                .body(addressDto);
    }
}
