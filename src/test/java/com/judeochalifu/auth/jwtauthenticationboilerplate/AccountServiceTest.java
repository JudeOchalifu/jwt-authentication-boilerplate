package com.judeochalifu.auth.jwtauthenticationboilerplate;

import com.judeochalifu.auth.jwtauthenticationboilerplate.constants.AccountType;
import com.judeochalifu.auth.jwtauthenticationboilerplate.controller.AccountController;
import com.judeochalifu.auth.jwtauthenticationboilerplate.domain.Account;
import com.judeochalifu.auth.jwtauthenticationboilerplate.exception.AccountAlreadyExistException;
import com.judeochalifu.auth.jwtauthenticationboilerplate.filter.JWTAuthenticationFilter;
import com.judeochalifu.auth.jwtauthenticationboilerplate.service.AccountService;
import com.judeochalifu.auth.jwtauthenticationboilerplate.utility.AccountsJsonReader;
import com.judeochalifu.auth.jwtauthenticationboilerplate.utility.DateTimeHelper;
import com.judeochalifu.auth.jwtauthenticationboilerplate.utility.EmailValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountServiceTest {
   
   private List<Account> accountList;
   
   
   @Mock
   private AuthenticationManager authenticationManager;
   
   @Mock
   private AccountService accountService;
   
   @InjectMocks
   private AccountController accountController;
   @Mock
   BCryptPasswordEncoder bCryptPasswordEncoder;
   @InjectMocks
   private final JWTAuthenticationFilter authenticationFilter = new JWTAuthenticationFilter(authenticationManager);
   
   @Before
   public void init() {
      MockitoAnnotations.initMocks(this);
      accountList = new ArrayList<>();
      accountList = AccountsJsonReader.readAccountsForTest();
   }
   
   @Test
   public void shouldSaveAccountSuccessfully() {
      final Account account = new Account();
      
      //account.setDateCreated(Timestamp.valueOf(LocalDateTime.of(2020,8,6,17,11)));
      account.setDateCreated(Timestamp.from(Instant.now()));
      account.setAccountType(AccountType.USER);
      account.setEmail("tester@yahoo.com");
      account.setFirstName("James");
      account.setLastName("Anderson");
      account.setUsername("janderson");
      account.setPassword("qwerty1845");
   
      ResponseEntity<String> signUpResponse = accountController.signUp(account);
      assertThat(signUpResponse.getStatusCodeValue()).isEqualTo(200);
   }
   
   @Test
   public void shouldSaveAccountSuccessfullyAtServiceLayer() {
      final Account account = new Account();
      
      //account.setDateCreated(Timestamp.valueOf(LocalDateTime.of(2020,8,6,17,11)));
      account.setDateCreated(Timestamp.from(Instant.now()));
      account.setAccountType(AccountType.USER);
      account.setEmail("tester@yahoo.com");
      account.setFirstName("James");
      account.setLastName("Anderson");
      account.setUsername("janderson");
      account.setPassword("qwerty1845");
   
      ResponseEntity<String> signUpResponse = accountController.signUp(account);
      assertThat(signUpResponse.getStatusCodeValue() == 200);
      //assertThat(Objects.requireNonNull(signUpResponse.getHeaders().getLocation()).getPath()).isEqualTo("/account/user/signup");
      when(accountService.saveUserAccount(account)).thenReturn(account);
      Account newAccount = accountService.saveUserAccount(account);
      assertThat(newAccount).isNotNull();
      verify(accountService, times(2)).saveUserAccount(any(Account.class));
      
   }
   
   @Test
   public void shouldFailIfPasswordInvalidOrEmpty() {
      final Account account = new Account();
   
      //account.setDateCreated(Timestamp.valueOf(LocalDateTime.of(2020,8,6,17,11)));
      account.setDateCreated(Timestamp.from(Instant.now()));
      account.setAccountType(AccountType.USER);
      account.setEmail("tester@yahoo.com");
      account.setFirstName("James");
      account.setLastName("Anderson");
      account.setUsername("janderson");
      account.setPassword("qwerty1845");
   
      assertTrue(account.getPassword().length() >= 8, "Password length must be >=  8");
      
   }
   
   @Test
   public void shouldFailIfAccountWithUsernameExist() {
      final Account account = new Account();
      
      //account.setDateCreated(Timestamp.valueOf(LocalDateTime.of(2020,8,6,17,11)));
      account.setDateCreated(Timestamp.from(Instant.now()));
      account.setAccountType(AccountType.USER);
      account.setEmail("test@yahoo.com");
      account.setFirstName("James");
      account.setLastName("Anderson");
      account.setUsername("janderson");
      account.setPassword("qwerty1845");
      
      given(accountService.getAccountByUsername(account.getUsername())).willReturn(account);
      
   }
   
   @Test
   public void shouldFailIfAccountWithEmailExist() {
      final Account account = new Account();
      
      //account.setDateCreated(Timestamp.valueOf(LocalDateTime.of(2020,8,6,17,11)));
      account.setDateCreated(Timestamp.from(Instant.now()));
      account.setAccountType(AccountType.USER);
      account.setEmail("testerx@yahoo.com");
      account.setFirstName("James");
      account.setLastName("Anderson");
      account.setUsername("janderson");
      account.setPassword("qwerty1845");
      
      given(accountService.getAccountByUsername(account.getEmail())).willReturn(account);
      
   }
   
   @Test
   public void shouldFailIfRegistrationDateInPastOrFuture() {
      final Account account = new Account();
      
      //account.setDateCreated(Timestamp.valueOf(LocalDateTime.of(2020,8,11,17,11)));
      account.setDateCreated(Timestamp.from(Instant.now()));
      account.setAccountType(AccountType.USER);
      account.setEmail("tester@yahoo.com");
      account.setFirstName("James");
      account.setLastName("Anderson");
      account.setUsername("janderson");
      account.setPassword("qwerty1845");
      assertFalse(DateTimeHelper.checkIfDateInPastOrFuture(account.getDateCreated()), "Registration date cannot be in the past of the future");
      
   }
   @Test
   public void shouldFailIfEmailInvalidOrNull() {
      final Account account = new Account();
   
      //account.setDateCreated(Timestamp.valueOf(LocalDateTime.of(2020,8,10,17,11)));
      account.setDateCreated(Timestamp.from(Instant.now()));
      account.setAccountType(AccountType.USER);
      account.setEmail("tester@yahoo.com");
      account.setFirstName("James");
      account.setLastName("Anderson");
      account.setUsername("janderson");
      account.setPassword("qwerty1845");
      
      assertNotNull(account.getEmail());
      assertTrue(EmailValidator.validateEmail(account.getEmail()), "The email address is not valid");
      
   }
   
   @Test
   public void shouldFailIfFieldsAreInvalid() {
      final Account account = new Account();
   
      account.setDateCreated(Timestamp.valueOf(LocalDateTime.of(2020,8,10,17,11)));
      //account.setDateCreated(Timestamp.from(Instant.now()));
      account.setAccountType(AccountType.USER);
      account.setEmail("tester@yahoo.com");
      account.setFirstName("James");
      account.setLastName("Anderson");
      account.setUsername("janderson");
      account.setPassword("qwerty1845");
      
      assertFalse(account.getFirstName().isEmpty(), "First name cannot be empty");
      assertFalse(account.getLastName().isEmpty(), "Last name cannot be empty");
      assertNotNull(account.getPassword(), "Password cannot be null");
      assertNotNull(account.getFirstName());
      assertNotNull(account.getLastName());
      assertNotNull(account.getUsername());
      assertNotNull(account.getDateCreated());
      assertNotNull(account.getAccountType());
   }
}
