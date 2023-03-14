package com.dmh.msusers.model.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOResponse implements Serializable {

    private String id;
    private String name;
    private String lastName;
    private String cpf;
    private String email;
    private String phone;

}
