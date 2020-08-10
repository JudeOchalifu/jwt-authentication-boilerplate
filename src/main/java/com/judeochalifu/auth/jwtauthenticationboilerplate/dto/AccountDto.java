package com.judeochalifu.auth.jwtauthenticationboilerplate.dto;

import com.judeochalifu.auth.jwtauthenticationboilerplate.constants.AccountType;

import java.sql.Timestamp;

public class AccountDto {
   
   private Long id;
   private String username;
   private String firstName;
   private String lastName;
   private Timestamp dateCreated;
   private AccountType accountType;
   private String email;
}
