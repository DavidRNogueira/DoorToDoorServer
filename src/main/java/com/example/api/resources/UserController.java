package com.example.api.resources;

import com.example.api.dao.UserDao;
import com.example.api.entity.UserEntity;
import com.example.api.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController (UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/{id}")
//    public String get (@PathVariable(name = "id") String id) {
//        UserEntity userEntity = userService.getUserById(id);
//        return userEntity.getOrg().getCity();
//    }

    @DeleteMapping("/{id}")
    public void delete (@PathVariable(name = "id") String id) {
        userService.deleteUser(id);
    }
}
