package com.judeochalifu.auth.jwtauthenticationboilerplate.controller;

import com.google.gson.Gson;
import com.judeochalifu.auth.jwtauthenticationboilerplate.domain.Account;
import com.judeochalifu.auth.jwtauthenticationboilerplate.repository.AccountRepository;
import com.judeochalifu.auth.jwtauthenticationboilerplate.response.ApiResponse;
import com.judeochalifu.auth.jwtauthenticationboilerplate.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.Instant;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Inject
    AccountService accountService;
    @Inject
    AccountRepository accountRepository;

    @GetMapping("/user/{username}")
    ResponseEntity<String> getAccountByUsername(@PathVariable String username) {
        Account account = accountService.getAccountByUsername(username);
        if (account == null) {
            ApiResponse<Account> apiResponse = new ApiResponse<Account>("No account associated with that username", HttpStatus.NOT_FOUND.value(),
                    null);
            return new ResponseEntity<String>(new Gson().toJson(apiResponse), HttpStatus.NOT_FOUND);
        } else {
            ApiResponse<Account> apiResponse = new ApiResponse<Account>("Success", HttpStatus.OK.value(),
                    accountRepository.getAccountByUsername(username).get());
            return new ResponseEntity<String>(new Gson().toJson(apiResponse), HttpStatus.OK);
        }


    }

    @GetMapping("/user/{email}")
    ResponseEntity<String> getAccountByEmail(@PathVariable String email) {
        Account account = accountService.getAccountByUsername(email);
        if (account == null) {
            ApiResponse<Account> apiResponse = new ApiResponse<Account>("Success", HttpStatus.OK.value(),
                    accountRepository.getAccountByEmail(email).get());
            return new ResponseEntity<String>(new Gson().toJson(apiResponse), HttpStatus.OK);
        } else  {
            ApiResponse<Account> apiResponse = new ApiResponse<Account>("No account associated with that email", HttpStatus.NOT_FOUND.value(),
                    null);
            return new ResponseEntity<String>(new Gson().toJson(apiResponse), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/account/new")
    ResponseEntity<String> signUp(@RequestBody Account account) {

        if (accountService.getAccountByEmail(account.getEmail()) != null) {
            ApiResponse<String> apiResponse = new ApiResponse<>("A user with that email already exist", HttpStatus.CONFLICT.value(), null);
            return new ResponseEntity<String>(new Gson().toJson(apiResponse), HttpStatus.CONFLICT);
        }
        if (accountService.getAccountByEmail(account.getEmail()) != null) {
            ApiResponse<String> apiResponse = new ApiResponse<>("A user with that username already exist", HttpStatus.CONFLICT.value(), null);
            return new ResponseEntity<String>(new Gson().toJson(apiResponse), HttpStatus.CONFLICT);
        }

        account.setDateCreated(Timestamp.from(Instant.now()));

        Account newAccount = accountService.saveUserAccount(account);
        ApiResponse<Account> apiResponse = new ApiResponse<>("Account created successfully", HttpStatus.OK.value(), newAccount);
        return new ResponseEntity<String>(new Gson().toJson(apiResponse), HttpStatus.OK);

    }
}