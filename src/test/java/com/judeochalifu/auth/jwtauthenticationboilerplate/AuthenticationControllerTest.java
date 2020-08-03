package com.judeochalifu.auth.jwtauthenticationboilerplate;

import com.judeochalifu.auth.jwtauthenticationboilerplate.constants.AccountType;
import com.judeochalifu.auth.jwtauthenticationboilerplate.controller.AuthenticationController;
import com.judeochalifu.auth.jwtauthenticationboilerplate.domain.Account;
import com.judeochalifu.auth.jwtauthenticationboilerplate.service.AccountService;

import com.judeochalifu.auth.jwtauthenticationboilerplate.utility.DateTimeHelper;
import com.judeochalifu.auth.jwtauthenticationboilerplate.utility.EmailValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationControllerTest {

   @InjectMocks
   private final AuthenticationController authenticationController = new AuthenticationController();

   @Mock
   private final AccountService accountService = new AccountService();

   @Mock
   private BCryptPasswordEncoder bCryptPasswordEncoder;

   @Before
   public void init() {
      MockitoAnnotations.initMocks(this);
      System.out.println("INIT=======>");
   }

   @Test
   public void testAuthenticationController() {
      String jsonString;
      Account account = new Account();

      //account.setDateCreated(Timestamp.valueOf(LocalDateTime.of(2020,8,6,17,11)));
      account.setDateCreated(Timestamp.from(Instant.now()));
      account.setAccountType(AccountType.USER);
      account.setEmail("test@yahoo.com");
      account.setFirstName("James");
      account.setLastName("Anderson");
      account.setUsername("janderson");
      account.setPassword("qwerty1845");


      assertNotNull(account.getEmail());
      assertFalse(account.getFirstName().isEmpty(), "First name cannot be empty");
      assertFalse(account.getLastName().isEmpty(), "Last name cannot be empty");
      assertNotNull(account.getPassword(), "Password cannot be null");
      assertNotNull(account.getFirstName());
      assertNotNull(account.getLastName());
      assertNotNull(account.getUsername());
      assertNotNull(account.getDateCreated());
      assertFalse(DateTimeHelper.checkIfDateInPastOrFuture(account.getDateCreated()), "Registration date cannot be in the past of the future");
      assertTrue(EmailValidator.validateEmail(account.getEmail()), "The email address is not valid");
      assertTrue(account.getPassword().length() >= 8, "Password length must be >=  8");
   }


   @Test
   public void testGetAccountByUsername() {
      Account account = new Account();
      account.setUsername("justice_major");
      assertFalse(account.getUsername().isEmpty(), "Must provide a username");

   }

   @Test
   public void testGetAccountByEmail() {
      Account account = new Account();
      account.setEmail("test@yahoo.com");
      assertFalse(account.getEmail().isEmpty(), "Must provide an email");
      assertTrue(EmailValidator.validateEmail(account.getEmail()), "The email address is not valid");

   }
}
