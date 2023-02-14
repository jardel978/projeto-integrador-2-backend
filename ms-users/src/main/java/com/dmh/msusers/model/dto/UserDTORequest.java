package com.dmh.msusers.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTORequest implements Serializable {

    private String id;
    @NotNull(message = "\"name\" is required.")
    private String name;
    @NotNull(message = "\"last name\" is required.")
    private String lastName;
    @NotNull(message = "\"cpf\" is required.")
    private String cpf;
    @NotNull(message = "\"email\" is required.")
    private String email;
    @NotNull(message = "\"phone\" is required.")
    private String phone;
    @NotNull(message = "\"password\" is required.")
    private String password;

}
