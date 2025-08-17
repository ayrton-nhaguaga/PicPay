package com.picpay.picpay.domain.user;

import com.picpay.picpay.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigDecimal;


@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public User(UserDTO data) {
        this.firstName = data.firstName();
        this.lastName = data.lastName();
        this.document = data.document();
        this.balance = data.balance();
        this.userType = data.userType();
        this.password = data.password();
        this.email = data.email();
    }
}
