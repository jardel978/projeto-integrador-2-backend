package com.dmh.msaccounts.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {

    private String id;
    private String name;
    private String lastName;
    private String cpf;
    private String email;
    private String phone;

}
