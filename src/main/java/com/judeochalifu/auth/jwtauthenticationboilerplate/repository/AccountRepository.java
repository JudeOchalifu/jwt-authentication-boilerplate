package com.judeochalifu.auth.jwtauthenticationboilerplate.repository;


import com.judeochalifu.auth.jwtauthenticationboilerplate.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> getAccountByEmail(String email);
    Optional<Account> getAccountByUsername(String username);
}
