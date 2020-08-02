package com.judeochalifu.auth.jwtauthenticationboilerplate.service;


import com.google.gson.Gson;
import com.judeochalifu.auth.jwtauthenticationboilerplate.domain.Account;
import com.judeochalifu.auth.jwtauthenticationboilerplate.repository.AccountRepository;
import com.judeochalifu.auth.jwtauthenticationboilerplate.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AccountService {

    @Inject
    AccountRepository accountRepository;

    public Account saveUserAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccountByEmail(String email) {
        if (accountRepository.getAccountByEmail(email).isPresent()) {
            return accountRepository.getAccountByEmail(email).get();
        }
        return null;
    }
    public Account getAccountByUsername(String username) {
        if (accountRepository.getAccountByUsername(username).isPresent()) {
            return accountRepository.getAccountByUsername(username).get();
        }
        return null;
    }


}
