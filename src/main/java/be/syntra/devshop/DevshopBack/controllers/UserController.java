package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.models.UserDto;
import be.syntra.devshop.DevshopBack.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<?> retrieveAllUsers() {
        return ResponseEntity
                .status(200)
                .body(userService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> retrieveUserById(@PathVariable Long userId) {
        return ResponseEntity
                .status(200)
                .body(userService.findById(userId));
    }

    /*
     *@Returns: 201-created code when our user's successfully saved
     */
    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        userService.save(userDto);
        return ResponseEntity
                .status(201)
                .body(userDto);
    }

}
