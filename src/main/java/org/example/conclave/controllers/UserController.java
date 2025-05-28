package org.example.conclave.controllers;

import org.example.conclave.dto.LoginRequestDTO;
import org.example.conclave.dto.RegistrationRequestDTO;
import org.example.conclave.models.User;
import org.example.conclave.repositories.UserRepository;
import org.example.conclave.services.AuthService;
import org.example.conclave.services.RegistrationService;
import org.example.conclave.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final RegistrationService registrationService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserController(UserService userService, AuthService authService, RegistrationService registrationService) {
        this.userService = userService;
        this.authService = authService;
        this.registrationService = registrationService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            User user = (User) userService.loadUserByUsername(loginRequest.getUsername());
            System.out.println(user.getPassword().toString());
            System.out.println(user.getUsername().toString());
            // Проверка пароля
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин или пароль");
            }
            String token = authService.generateAndSetToken(user);
            return ResponseEntity.ok().header("Authorization", "Bearer " + token).body("Успешный вход");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин или пароль");
        }
    }
    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody RegistrationRequestDTO registrationRequest) {
        try {
            System.out.println("Starting registration process for user: " + registrationRequest.getUsername());

            String token = registrationService.registerUser(
                    registrationRequest.getUsername(),
                    registrationRequest.getPassword()
            );

            System.out.println("User registered successfully: " + registrationRequest.getUsername());
            System.out.println(token);
            // Add the Authorization token to the response header
            return ResponseEntity.ok().header("Authorization", "Bearer " + token).body("Успешный вход");
        } catch (IllegalArgumentException e) {
            System.err.println("Registration error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid registration data: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error during registration: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
        }
    }
}
