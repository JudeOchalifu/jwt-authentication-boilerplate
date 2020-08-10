package com.judeochalifu.auth.jwtauthenticationboilerplate.controller;

import com.google.gson.Gson;
import com.judeochalifu.auth.jwtauthenticationboilerplate.domain.Account;
import com.judeochalifu.auth.jwtauthenticationboilerplate.repository.AccountRepository;
import com.judeochalifu.auth.jwtauthenticationboilerplate.response.ApiResponse;
import com.judeochalifu.auth.jwtauthenticationboilerplate.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.Instant;

@RestController
@RequestMapping("account")
public class AccountController {

    @Inject
    AccountService accountService;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public AccountController(BCryptPasswordEncoder bCryptPasswordEncoder) {
       this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @GetMapping("/user/username/{username}")
    public ResponseEntity<String> getAccountByUsername(@PathVariable String username) {
        Account account = accountService.getAccountByUsername(username);
        if (account == null) {
            ApiResponse<Account> apiResponse = new ApiResponse<Account>("No account associated with that username", HttpStatus.NOT_FOUND.value(),
                    null);
            return new ResponseEntity<String>(new Gson().toJson(apiResponse), HttpStatus.NOT_FOUND);
        } else {
	   account.setPassword("");
            ApiResponse<Account> apiResponse = new ApiResponse<Account>("Success", HttpStatus.OK.value(),
                    account);
            return new ResponseEntity<String>(new Gson().toJson(apiResponse), HttpStatus.OK);
        }


    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<String> getAccountByEmail(@PathVariable String email) {
        Account account = accountService.getAccountByEmail(email);
        if (account == null) {
	   ApiResponse<Account> apiResponse = new ApiResponse<Account>("No account associated with that email", HttpStatus.NOT_FOUND.value(),
		   null);
	   return new ResponseEntity<String>(new Gson().toJson(apiResponse), HttpStatus.NOT_FOUND);
        } else  {
           account.setPassword("");
	   ApiResponse<Account> apiResponse = new ApiResponse<>("Success", HttpStatus.OK.value(),
		   account);
	   return new ResponseEntity<String>(new Gson().toJson(apiResponse), HttpStatus.OK);
        }

    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody Account account) {

        if (accountService.getAccountByEmail(account.getEmail()) != null) {
            ApiResponse<String> apiResponse = new ApiResponse<>("A user with that email already exist", HttpStatus.CONFLICT.value(), null);
            return new ResponseEntity<String>(new Gson().toJson(apiResponse), HttpStatus.CONFLICT);
        }
        if (accountService.getAccountByUsername(account.getUsername()) != null) {
            ApiResponse<String> apiResponse = new ApiResponse<>("A user with that username already exist", HttpStatus.CONFLICT.value(), null);
            return new ResponseEntity<String>(new Gson().toJson(apiResponse), HttpStatus.CONFLICT);
        }

        account.setDateCreated(Timestamp.from(Instant.now()));
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));

        Account newAccount = accountService.saveUserAccount(account);
        ApiResponse<Account> apiResponse = new ApiResponse<>("Account created successfully", HttpStatus.OK.value(), newAccount);
        return new ResponseEntity<String>(new Gson().toJson(apiResponse), HttpStatus.OK);

    }
}