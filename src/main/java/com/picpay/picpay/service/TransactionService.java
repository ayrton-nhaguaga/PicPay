package com.picpay.picpay.service;

import com.picpay.picpay.domain.transaction.Transaction;
import com.picpay.picpay.domain.user.User;
import com.picpay.picpay.dto.TransactionDTO;
import com.picpay.picpay.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RestTemplate restTemplate; // classe para fazer comunicacoes http entre servicos

    public Transaction createTransaction(TransactionDTO transaction) throws Exception{
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.receiverId());

        userService.validateTransaction(sender, transaction.value());

        boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());
        if (!isAuthorized){
            throw new Exception("Transacao nao autorizada");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());


        // atualizar os saldos dos usuarios e salvar a transacao na BD
        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        this.transactionRepository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transacao realizada com sucesso");

        this.notificationService.sendNotification(receiver, "Transacao recebida com sucesso");

        return newTransaction;

    }

    public boolean authorizeTransaction(User sender, BigDecimal value){
       ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);

       if (authorizationResponse.getStatusCode() == HttpStatus.OK ){
           String message = (String) authorizationResponse.getBody().get("message");
           return "Autorizado".equalsIgnoreCase(message);
       } else return false;
    }
}
