package be.syntra.devshop.DevshopBack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devshop")
public class TstRestController {

    @GetMapping(value = "/test", produces = "application/JSON")
    public ResponseEntity<String> displayTstString() {
        return ResponseEntity.ok().body("DevShop");
    }
}
