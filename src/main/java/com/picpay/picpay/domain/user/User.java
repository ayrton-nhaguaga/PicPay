package com.picpay.picpay.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigDecimal;


@Document(collection = "users")
@Data
@AllArgsConstructor

public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;

    @Indexed(unique = true) // nao permite dois documentos iguais
    private String document;

    @Indexed(unique = true) // nao permite dois emails iguais
    private String email;

    private String password;

    private BigDecimal balance;

    private UserType userType;
}
