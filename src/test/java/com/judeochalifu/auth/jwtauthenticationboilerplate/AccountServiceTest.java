package com.judeochalifu.auth.jwtauthenticationboilerplate;

import com.judeochalifu.auth.jwtauthenticationboilerplate.constants.AccountType;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountServiceTest {
   
   private List<Account> accountList;
   
   
   @Mock
   private AuthenticationManager authenticationManager;
   
   @Mock
   private AccountService accountService;
   
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
      
      
      given(accountService.getAccountByEmail(account.getEmail())).willReturn(account);
      given(accountService.getAccountByUsername(account.getUsername())).willReturn(account);
      when(accountService.saveUserAccount(account)).thenReturn(account);
      Account newAccount = accountService.saveUserAccount(account);
      System.out.println(newAccount == null ? "IS NULLLLLLLLL" : "IS NOT NULLLLLLLLLLLLLL");
      assertThat(newAccount).isNotNull();
      verify(accountService).saveUserAccount(any(Account.class));
      
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
      
      /*assertThrows(AccountAlreadyExistException.class, () -> {
	 accountService.saveUserAccount(account);
      });*/
   }
}
