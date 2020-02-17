package com.bci.apps.bcitest.util;

import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

  public static String getToken(String username) throws Exception {
    
    long ttlMillis = 300000;
    long nowMillis = System.currentTimeMillis();

    Date expiration = new Date(nowMillis + ttlMillis);
    return Jwts.builder()
        .setSubject("users/TzMUocMF4p")
        .setExpiration(expiration)
        .claim("name", username)
        .claim("scope", "admin")
        .signWith(
          SignatureAlgorithm.HS256,
          "5Sbbgf98gfGAUSDKiUZy3iHBbb6Q4yTEk04MTKKjNpM=".getBytes("UTF-8")
        )
        .compact();
    
//    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    String base64Key = Encoders.BASE64.encode(key.getEncoded());
//
//    String token = Jwts.builder().setSubject(username)
//        .setExpiration(new Date(System.currentTimeMillis() + 120000))
//        .signWith(SignatureAlgorithm.HS256, base64Key).compact();
//
//    return token;
  }

  public static boolean checkToken(String token){
        
    try {
      Jwts.parser()
      .setSigningKey("5Sbbgf98gfGAUSDKiUZy3iHBbb6Q4yTEk04MTKKjNpM=".getBytes("UTF-8"))
      .parseClaimsJws(token);
    }
    catch(Exception ex) {
      return false;
    }
    return true;
    
    
//    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    String base64Key = Encoders.BASE64.encode(key.getEncoded());
//
//    if (token != null) {
//      String user = Jwts.parser().setSigningKey(base64Key)
//          .parseClaimsJws(token).getBody().getSubject();
//      return user != null;
//    }
//    return false;
  }

}
