package com.picpay.picpay.service;

import com.picpay.picpay.domain.user.User;
import com.picpay.picpay.domain.user.UserType;
import com.picpay.picpay.dto.UserDTO;
import com.picpay.picpay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Usuario do tipo lojista nao esta autorizado a realizar transacoes");
        }

        if (sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo insuficiente");
        }
    }

    public User findUserById(String id) throws Exception{
        return this.userRepository.findUserById(id).orElseThrow(() -> new Exception("Usuario nao encontrado"));
    }

    public User createUser(UserDTO data){
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public void  saveUser(User user){
        this.userRepository.save(user);
    }
}
