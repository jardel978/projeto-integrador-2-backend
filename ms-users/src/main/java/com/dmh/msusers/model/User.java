package com.dmh.msusers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String name;
    private String lastName;
    private String cpf;
    private String email;
    private String phone;
    private String password;

}
