package com.dmh.msusers.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPatchDTORequest implements Serializable {

    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String password;

}
