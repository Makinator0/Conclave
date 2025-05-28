package org.example.conclave.services;
import jakarta.servlet.http.HttpServletResponse;
import org.example.conclave.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final UserService userService;

    private final AuthService authService;

    public RegistrationService(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    public String registerUser(
            String name,
            String password) {
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        boolean isCreated = userService.createUser(user);
        if (!isCreated) {
            throw new IllegalArgumentException("User registration failed.");
        }

        return authService.generateAndSetToken(user);
    }
}
