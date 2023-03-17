package com.dmh.msaccounts.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transferences")
public class Transferences extends Transactions implements Serializable {

    @ManyToOne
    private Accounts accountsDestiny;
}
