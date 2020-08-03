package com.judeochalifu.auth.jwtauthenticationboilerplate;

import com.judeochalifu.auth.jwtauthenticationboilerplate.constants.AccountType;
import com.judeochalifu.auth.jwtauthenticationboilerplate.controller.AuthenticationController;
import com.judeochalifu.auth.jwtauthenticationboilerplate.domain.Account;
import com.judeochalifu.auth.jwtauthenticationboilerplate.service.AccountService;

import com.judeochalifu.auth.jwtauthenticationboilerplate.utility.EmailValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationControllerTest {

    @InjectMocks
    private final AuthenticationController authenticationController = new AuthenticationController();

    @Mock
    private final AccountService accountService = new AccountService();

    @Mock
    private  BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        System.out.println("INIT=======>");
    }

    @Test
    public void testAuthenticationController() {
        String jsonString ;
        Account account = new Account();

        account.setDateCreated(Timestamp.from(Instant.now()));
        account.setAccountType(AccountType.USER);
        account.setEmail("test@yahoo.com");
        account.setFirstName("James");
        account.setLastName("Anderson");
        account.setUsername("janderson");
        account.setPassword("qwerty");


        assertNotNull(account.getEmail());
        assertNotNull(account.getPassword(), "Password cannot be null");
        assertNotNull(account.getFirstName());
        assertNotNull(account.getLastName());
        assertNotNull(account.getUsername());
        assertNotNull(account.getDateCreated());
        assertTrue(EmailValidator.validateEmail(account.getEmail()), "The email address is not valid");
        assertTrue(account.getPassword().length() > 5, "Password length must be >=  5");
        //assert(account.getPassword().length() >= 5);

        //authenticationController.signUp(account);
    }


    @Test
    public void testGetAccountByUsername() {
        Account account = new Account();
        account.setUsername("jochalifu");

    }
}
