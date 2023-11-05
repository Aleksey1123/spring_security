package ar.pet_project.security.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin("*")
public class UserController {

    @GetMapping("/")
    public String helloUserController() {
        return "User access level";
    }
}
