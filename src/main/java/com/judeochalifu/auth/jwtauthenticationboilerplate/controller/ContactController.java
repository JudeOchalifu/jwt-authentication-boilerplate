package com.judeochalifu.auth.jwtauthenticationboilerplate.controller;


import com.google.gson.Gson;
import com.judeochalifu.auth.jwtauthenticationboilerplate.domain.Contact;
import com.judeochalifu.auth.jwtauthenticationboilerplate.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("contact")
public class ContactController {


    private ContactService contactService;
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/new")
    public ResponseEntity<String> addNewContact(@RequestBody Contact contact) {
        System.out.println(new Gson().toJson(contact));
        if (contactService.findByEmail(contact.getEmail()) != null ){
            return new ResponseEntity<>("A contact with that email already exists", HttpStatus.CONFLICT);
        }  else {
            this.contactService.addContact(contact);
            return new ResponseEntity<>("Success. Contact saved successfully", HttpStatus.OK);
        }
    }

    @GetMapping("/all")
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getContact(@PathVariable Long id) {
        Contact contact = contactService.getContactById(id);
        if (contact != null) {
            Gson gson = new Gson();
            return new ResponseEntity<>(gson.toJson(contact), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No contact with that ID found", HttpStatus.NOT_FOUND);
        }
    }
}
