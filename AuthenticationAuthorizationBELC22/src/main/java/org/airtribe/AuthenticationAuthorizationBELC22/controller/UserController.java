package org.airtribe.AuthenticationAuthorizationBELC22.controller;

import java.util.UUID;
import org.airtribe.AuthenticationAuthorizationBELC22.entity.User;
import org.airtribe.AuthenticationAuthorizationBELC22.entity.UserDTO;
import org.airtribe.AuthenticationAuthorizationBELC22.service.AuthenticationAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.*;


@RestController
public class UserController {
  @Autowired
  private AuthenticationAuthorizationService _authenticationAuthorizationService;


  @PostMapping("/signup")
  public User registerUser(@RequestBody UserDTO userDTO) {
    User user = _authenticationAuthorizationService.registerUser(userDTO);
    String verificationToken = UUID.randomUUID().toString();
    String verificationUrl = "http://localhost:5006/verifyRegistration?token=" + verificationToken;
    System.out.println(verificationUrl);
    _authenticationAuthorizationService.persistVerificationToken(verificationToken, user);
    return user;
  }

  // @GetMapping"/hello " -> sigin

  @PostMapping("/verifyRegistration")
  public User verifyRegistration(@RequestParam("token") String token) {
    return _authenticationAuthorizationService.verifyRegistrationTokenAndEnableUser(token);
  }

  @PostMapping("/signin")
  public String signin(@RequestParam("username") String username, @RequestParam("password") String password) {
    return _authenticationAuthorizationService.login(username, password);
  }

  // spring boot security
  // authenticated -> session based authentication
  // not protected
  @GetMapping("/helloWorld")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public String helloWorld() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    System.out.println(authentication.getAuthorities());

    // Write the authentication logic here
    return "Hello World!";
  }

  @GetMapping("/helloIndia")
  public String helloIndia() {
    return "Hello India";
  }
}
