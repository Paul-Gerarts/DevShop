package be.syntra.devshop.DevshopBack.security.controllers;

import be.syntra.devshop.DevshopBack.exceptions.UserAlreadyRegisteredException;
import be.syntra.devshop.DevshopBack.exceptions.UserRoleNotFoundException;
import be.syntra.devshop.DevshopBack.security.controllers.dtos.LogInDto;
import be.syntra.devshop.DevshopBack.security.controllers.dtos.RegisterDto;
import be.syntra.devshop.DevshopBack.security.entities.JWTToken;
import be.syntra.devshop.DevshopBack.security.entities.UserRole;
import be.syntra.devshop.DevshopBack.security.services.UserRoleService;
import be.syntra.devshop.DevshopBack.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static be.syntra.devshop.DevshopBack.security.entities.UserRoles.ROLE_USER;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private UserServiceImpl userService;
    private UserRoleService userRoleService;

    @Autowired
    public AuthorizationController(UserServiceImpl userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    /*
     *@Return 200 OK code when securely logged in
     */
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LogInDto logInDto) {
        JWTToken jwtToken = userService.getNewJwtToken(logInDto.getEmail(), logInDto.getPassword());
        return ResponseEntity.ok(jwtToken);
    }

    /*
     *@Return 201 CREATED code for registering new User
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerNewCustomer(@RequestBody RegisterDto registerDto) throws UserRoleNotFoundException, UserAlreadyRegisteredException {
        List<UserRole> userRoles = new ArrayList<>();
        userRoles.add(userRoleService.findByRoleName(ROLE_USER.name()));
        userService.registerUser(
                registerDto.getEmail(),
                registerDto.getPassword(),
                registerDto.getFirstName(),
                registerDto.getLastName(),
                userRoles,
                registerDto.getAddress());
        return ResponseEntity.status(201).body(registerDto);
    }

}
