package com.judeochalifu.auth.jwtauthenticationboilerplate.service;
import com.judeochalifu.auth.jwtauthenticationboilerplate.domain.Account;
import com.judeochalifu.auth.jwtauthenticationboilerplate.repository.AccountRepository;
import org.springframework.stereotype.Service;


@Service
public class AccountService  {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository ){
        this.accountRepository = accountRepository;
    }

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
    
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }


}
