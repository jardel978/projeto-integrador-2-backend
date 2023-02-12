package com.dmh.msusers.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOResponse {

    private String id;
    private String name;
    private String lastName;
    private String cpf;
    private String email;
    private String phone;

}
