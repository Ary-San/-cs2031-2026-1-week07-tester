package com.example.flyaway.auth;

import com.example.flyaway.dto.AuthTokenDTO;
import com.example.flyaway.dto.LoginDTO;
import com.example.flyaway.model.User;
import com.example.flyaway.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;

    public AuthService(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public AuthTokenDTO login(LoginDTO login) {
        User user = userService.findByEmail(login.getEmail());
        if (!user.getPassword().equals(login.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password");
        }
        return new AuthTokenDTO(jwtService.createToken(user.getId(), user.getEmail()));
    }
}
