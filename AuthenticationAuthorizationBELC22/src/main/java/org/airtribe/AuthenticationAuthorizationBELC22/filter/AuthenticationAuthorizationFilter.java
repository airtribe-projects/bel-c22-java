package org.airtribe.AuthenticationAuthorizationBELC22.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.airtribe.AuthenticationAuthorizationBELC22.util.JwtUtil;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


// helps spring boot understand how many times should the filter be called and where
@Component
@Order(2)
public class AuthenticationAuthorizationFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // Parse the Headers -> Authorization header
    // if the token is not present -> Unauthenticated
    // if the token is present
       // -> check for sinnature -> if the token is actually generated  is by this
    // if the signatures match -> if the token is expired -> unauthenticatioed
    // if the token is valid -> filter chain to invoke the next thing

    String token = request.getHeader("Authorization");

    if (token == null || token.isEmpty() || token.isBlank()) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Missing Authorization token");
      return;
    }

    Claims parsedClaims;
    try {
      parsedClaims = JwtUtil.validateJwtToken(token);
      List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
      String role = parsedClaims.get("roles", String.class);
      authorityList.add(new SimpleGrantedAuthority(role));
      SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(parsedClaims.getSubject(), null, authorityList));
    } catch (SignatureException signatureException) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Invalid signature of the token");
      return;
    } catch (MalformedJwtException malformedJwtException) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Malformed JWT");
      return;
    } catch (ExpiredJwtException expiredJwtException) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Expired JWT");
      return;
    }

    System.out.println(parsedClaims);
    filterChain.doFilter(request, response);

  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return request.getRequestURI().contains("signin") || request.getRequestURI().contains("signup") || request.getRequestURI().contains("helloIndia");
  }
}
