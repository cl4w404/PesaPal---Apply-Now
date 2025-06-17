package com.devcorp.bank_proj.controller;

import com.devcorp.bank_proj.dto.UserDto;
import com.devcorp.bank_proj.models.User;
import com.devcorp.bank_proj.response.Response;
import com.devcorp.bank_proj.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
     public Response addUser(@RequestBody UserDto user){
        return userService.createUser(user);
    }
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
