package com.picpay.picpay.domain.transaction;

import com.picpay.picpay.domain.user.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "transactions")
@Data
public class Transaction {

    @Id
    private String id;

    private BigDecimal amount;

    private User sender;

    private User receiver;

    private LocalDateTime timestamp;
}
