package org.example.conclave.controllers;

import org.example.conclave.dto.LoginRequestDTO;
import org.example.conclave.models.User;
import org.example.conclave.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }




    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            System.out.println(loginRequest);
            User user = (User) userService.loadUserByUsername(loginRequest.getUsername());
            System.out.println(user.getPassword());
            System.out.println(user.getUsername());
                return ResponseEntity.ok("Успішний вхід");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин или пароль");
        }
    }

}
