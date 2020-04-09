package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;
import be.syntra.devshop.DevshopBack.services.UserService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private DozerBeanMapper dozerMapper;

    @Autowired
    public UserController(UserService userService,
                          DozerBeanMapper dozerMapper) {
        this.userService = userService;
        this.dozerMapper = dozerMapper;
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
        userService.save(dozerMapper.map(userDto, User.class));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userDto);
    }

}
