package com.judeochalifu.auth.jwtauthenticationboilerplate.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.judeochalifu.auth.jwtauthenticationboilerplate.domain.Account;
import sun.rmi.runtime.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AccountsJsonReader {
   private static final Logger logger = Logger.getLogger(AccountsJsonReader.class.getSimpleName());
   
   public static List<Account> readAccountsForTest() {
      try {
	 ObjectMapper mapper = new ObjectMapper();
	 mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
	 TypeReference<List<Account>> typeReference = new TypeReference<List<Account>>() {
	 };
	 InputStream inputStream = TypeReference.class.getResourceAsStream("/json/accounts.json");
	 return mapper.readValue(inputStream, typeReference);
	 //accounts.forEach(s -> accountService.save(s));
      } catch (Exception exception) {
	 logger.severe("An exception occurred while reading accounts { " + exception.getMessage() + " }");
      }
      return new ArrayList<>();
   }
   
   
}
