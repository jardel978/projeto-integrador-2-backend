package com.dmh.msaccounts.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "accounts")
public class Accounts  implements Serializable {

    @Id
    private String id;
    private String account;
    private String userId;
    private Set<Cards> cards;

}
