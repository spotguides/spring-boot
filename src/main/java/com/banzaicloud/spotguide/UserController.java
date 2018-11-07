package com.banzaicloud.spotguide;

import com.banzaicloud.spotguide.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String health() {
        return "OK";
    }


    @PostMapping(path = "/users")
    public User addNewUser(@RequestBody User user) {

        userRepository.save(user);

        return user;
    }

    @GetMapping(path = "/users")
    public Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
}
