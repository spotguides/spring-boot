package com.banzaicloud.spotguide;

import com.banzaicloud.spotguide.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found")
class UserNotFoundException extends RuntimeException {
}

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/users")
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping(path = "/users")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public User getUser(@PathVariable Integer id) {
        var user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return user.get();
    }

    @PutMapping(path = "/users/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User user) {
        var oldUser = userRepository.findById(id);
        if (oldUser.isEmpty()) {
            throw new UserNotFoundException();
        }
        user.setId(oldUser.get().getId());
        return userRepository.save(user);
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }
}
