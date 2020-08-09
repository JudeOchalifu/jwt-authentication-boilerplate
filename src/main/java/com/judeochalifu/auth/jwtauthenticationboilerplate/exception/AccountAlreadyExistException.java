package com.judeochalifu.auth.jwtauthenticationboilerplate.exception;

public class AccountAlreadyExistException extends Exception {
   
   private String message;
   
   public AccountAlreadyExistException(String message, Throwable error) {
      super(message, error);
      this.message = message;
   }
   
   @Override
   public String getMessage() {
      return message;
   }
}
