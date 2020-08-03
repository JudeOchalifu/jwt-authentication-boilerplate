package com.judeochalifu.auth.jwtauthenticationboilerplate.utility;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeHelper {

   public static boolean checkIfDateInPastOrFuture(Timestamp timestamp) {
      LocalDate today = LocalDate.now();
      LocalDate yesterday = today.minusDays(1);
      LocalDate tomorrow = today.plusDays(1);

      if (timestamp.toLocalDateTime().toLocalDate().equals(tomorrow)) {
         return true;
      }

      if (timestamp.toLocalDateTime().toLocalDate().equals(yesterday)) {
         return true;
      }
      return false;
   }
}
