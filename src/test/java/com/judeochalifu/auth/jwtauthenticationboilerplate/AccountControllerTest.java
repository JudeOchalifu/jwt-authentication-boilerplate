package com.judeochalifu.auth.jwtauthenticationboilerplate;

import com.judeochalifu.auth.jwtauthenticationboilerplate.constants.AccountType;
import com.judeochalifu.auth.jwtauthenticationboilerplate.controller.AccountController;
import com.judeochalifu.auth.jwtauthenticationboilerplate.domain.Account;
import com.judeochalifu.auth.jwtauthenticationboilerplate.repository.AccountRepository;
import com.judeochalifu.auth.jwtauthenticationboilerplate.service.AccountService;

import com.judeochalifu.auth.jwtauthenticationboilerplate.utility.EmailValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AccountControllerTest {
   
   @Mock
   BCryptPasswordEncoder bCryptPasswordEncoder;
   
   @InjectMocks
   private final AccountController accountController = new AccountController(bCryptPasswordEncoder);
   
   /*@Autowired
   private MockMvc mockMvc;*/
   
   
   @Mock
   private AccountRepository accountRepository;
   
   @Mock
   private final AccountService accountService = new AccountService(accountRepository);
   
   @Before
   public void init() {
      MockitoAnnotations.initMocks(this);
      System.out.println("INIT=======>");
   }
   
   @Test
   
   public void testAuthenticationController() {
   
   
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
   
   @Test
   public void shouldFetchAUserById() {
      
      final Long id = 1L;
      final Account account = new Account();
   
      //account.setDateCreated(Timestamp.valueOf(LocalDateTime.of(2020,8,6,17,11)));
      account.setDateCreated(Timestamp.from(Instant.now()));
      account.setAccountType(AccountType.USER);
      account.setEmail("test@yahoo.com");
      account.setFirstName("James");
      account.setLastName("Anderson");
      account.setUsername("janderson");
      account.setPassword("qwerty1845");
      account.setId(3L);
      
      given(accountService.findById(id)).willReturn(account);
      
      //this.mockMvc.perform()
      
   }
}
