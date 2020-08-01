package com.judeochalifu.auth.jwtauthenticationboilerplate.constants;

public enum AccountType {

    ADMIN ("Admin"),
    USER ("User");

    String textRepresentation;

    AccountType(String textRepresentation) {
        this.textRepresentation = textRepresentation;
    }

    public String getTextRepresentation() {
        return this.textRepresentation;
    }
}
