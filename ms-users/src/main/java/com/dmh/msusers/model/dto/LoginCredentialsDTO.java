package com.dmh.msusers.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginCredentialsDTO implements Serializable {

    @NotNull(message = "email is required.")
    private String email;
    @NotNull(message = "password is required.")
    private String password;

}
