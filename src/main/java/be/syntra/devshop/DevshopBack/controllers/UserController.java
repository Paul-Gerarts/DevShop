package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;
import be.syntra.devshop.DevshopBack.services.UserService;
import be.syntra.devshop.DevshopBack.services.utilities.UserMapperUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private UserMapperUtility userMapperUtility;

    @Autowired
    public UserController(UserService userService,
                          UserMapperUtility userMapperUtility) {
        this.userService = userService;
        this.userMapperUtility = userMapperUtility;
    }

    @GetMapping()
    public ResponseEntity<List<User>> retrieveAllUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findAll());
    }

    /*
     *@Returns: 201-created code when our user's successfully saved
     */
    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        userService.save(userMapperUtility.convertToUser(userDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userDto);
    }

}
