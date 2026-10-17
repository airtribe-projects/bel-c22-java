package org.airtribe.AuthenticationAuthorizationBELC22.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.airtribe.AuthenticationAuthorizationBELC22.entity.User;


public class JwtUtil {

  public static String generateJwtToken(User user) {

    return Jwts.builder().subject(user.getUsername())
        .setIssuedAt(new Date())
        .claim("roles", "ROLE_" + user.getRole())
        .claim("dummy", true)
        .setExpiration(new Date(System.currentTimeMillis() + 8 * 60 * 60 * 1000))
        .signWith(SignatureAlgorithm.HS256, "AirtribeBelC22AuthenticationAuthorizationAirtribeBelC22AuthenticationAuthorizationAirtribeBelC22AuthenticationAuthorizationAirtribeBelC22AuthenticationAuthorizationAirtribeBelC22AuthenticationAuthorization")
        .compact();
  }

  public static Claims validateJwtToken(String token) {
    Claims claims = Jwts.parser().setSigningKey("AirtribeBelC22AuthenticationAuthorizationAirtribeBelC22AuthenticationAuthorizationAirtribeBelC22AuthenticationAuthorizationAirtribeBelC22AuthenticationAuthorizationAirtribeBelC22AuthenticationAuthorization")
        .build().parseClaimsJws(token).getPayload();
    return claims;
  }
}

// Vaults -> vault provider
// Hashicorp
// KMS