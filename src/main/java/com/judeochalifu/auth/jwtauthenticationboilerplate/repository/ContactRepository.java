package com.judeochalifu.auth.jwtauthenticationboilerplate.repository;


import com.judeochalifu.auth.jwtauthenticationboilerplate.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByEmail(String email);
}


