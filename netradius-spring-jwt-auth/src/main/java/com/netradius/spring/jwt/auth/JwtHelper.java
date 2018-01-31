package com.netradius.spring.jwt.auth;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;

public class JwtHelper {

  protected static SecureRandom secureRandom = new SecureRandom();

  public static Key generateHS512Key() {
    try {
      KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA512");
      keyGenerator.init(secureRandom);
      return keyGenerator.generateKey();
    } catch (NoSuchAlgorithmException x) {
      throw new IllegalStateException(x);
    }
  }

}
