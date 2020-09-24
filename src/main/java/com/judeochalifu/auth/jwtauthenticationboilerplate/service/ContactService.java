package com.judeochalifu.auth.jwtauthenticationboilerplate.service;


import com.google.gson.Gson;
import com.judeochalifu.auth.jwtauthenticationboilerplate.domain.Contact;
import com.judeochalifu.auth.jwtauthenticationboilerplate.repository.ContactRepository;
import com.judeochalifu.auth.jwtauthenticationboilerplate.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void addContact(Contact contact) {
        this.contactRepository.save(contact);
    }

    public Contact getContactById(Long id) {
        return this.contactRepository.findById(id).orElse(null);
    }

    public List<Contact> getAllContacts() {
        return this.contactRepository.findAll();
    }

    public Contact findByEmail(String email) {
        return contactRepository.findByEmail(email).orElse(null);
    }
}
