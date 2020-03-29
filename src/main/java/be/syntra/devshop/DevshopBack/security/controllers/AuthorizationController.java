package be.syntra.devshop.DevshopBack.security.controllers;

import be.syntra.devshop.DevshopBack.security.controllers.dtos.LogInDto;
import be.syntra.devshop.DevshopBack.security.controllers.dtos.RegisterDto;
import be.syntra.devshop.DevshopBack.security.entities.JWTToken;
import be.syntra.devshop.DevshopBack.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private UserServiceImpl userService;

    @Autowired
    public AuthorizationController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /*
     *@Return 200 OK code when securely logged in
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LogInDto logInDto) {
        JWTToken jwtToken = userService.getNewJwtToken(logInDto.getUserName(), logInDto.getPassword());
        return ResponseEntity.ok(jwtToken);
    }

    /*
     *@Return 201 CREATED code for registering new User
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerNewCustomer(@RequestBody RegisterDto registerDto) {
        userService.registerUser(registerDto);
        return ResponseEntity.status(201).body(registerDto);
    }

}
