package be.syntra.devshop.DevshopBack.security.controllers;

import be.syntra.devshop.DevshopBack.security.controllers.dtos.LogInDto;
import be.syntra.devshop.DevshopBack.security.models.JwtToken;
import be.syntra.devshop.DevshopBack.security.services.UserRoleService;
import be.syntra.devshop.DevshopBack.security.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private UserService userService;
    private UserRoleService userRoleService;

    public AuthorizationController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LogInDto logInDto) {
        JwtToken jwtToken = userService.getNewJwtToken(logInDto)
    }

}
