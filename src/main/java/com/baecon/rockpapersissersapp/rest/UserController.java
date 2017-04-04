package com.baecon.rockpapersissersapp.rest;

import com.baecon.rockpapersissersapp.model.User;
import com.baecon.rockpapersissersapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.baecon.rockpapersissersapp.util.ApiConstants.REGISTRATION;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index() {
        return "Beacon Backend!";
    }

    @RequestMapping(value = REGISTRATION, method = RequestMethod.POST)
    public User register(@RequestParam("name") String name) {
        return userService.registerUser(name);
    }

}
