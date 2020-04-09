package be.syntra.devshop.DevshopBack.security.controllers;

import be.syntra.devshop.DevshopBack.security.controllers.dtos.LogInDto;
import be.syntra.devshop.DevshopBack.security.controllers.dtos.RegisterDto;
import be.syntra.devshop.DevshopBack.security.entities.JWTToken;
import be.syntra.devshop.DevshopBack.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private UserService userService;

    @Autowired
    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    /*
     *@Return 200 OK code when securely logged in
     */
    @PostMapping("/login")
    public ResponseEntity<JWTToken> login(@RequestBody LogInDto logInDto) {
        JWTToken jwtToken = userService.getNewJwtToken(logInDto.getUserName(), logInDto.getPassword());
        return ResponseEntity.ok(jwtToken);
    }

    /*
     *@Return 201 CREATED code for registering new User
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterDto> registerNewCustomer(@RequestBody RegisterDto registerDto) {
        userService.registerUser(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerDto);
    }

}
