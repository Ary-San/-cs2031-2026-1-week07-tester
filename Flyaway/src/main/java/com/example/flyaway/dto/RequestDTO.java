package com.example.hola.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RequestDTO {

    @NotNull
    @NotEmpty
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    
}
