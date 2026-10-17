package org.airtribe.AuthenticationAuthorizationBELC22.service;

import java.util.Date;
import java.util.Optional;
import org.airtribe.AuthenticationAuthorizationBELC22.entity.User;
import org.airtribe.AuthenticationAuthorizationBELC22.entity.UserDTO;
import org.airtribe.AuthenticationAuthorizationBELC22.entity.VerificationToken;
import org.airtribe.AuthenticationAuthorizationBELC22.repository.UserRepository;
import org.airtribe.AuthenticationAuthorizationBELC22.repository.VerificationTokenRepository;
import org.airtribe.AuthenticationAuthorizationBELC22.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationAuthorizationService implements UserDetailsService {

  @Autowired
  private UserRepository _userRepository;

  @Autowired
  private PasswordEncoder _passwordEncoder;

  @Autowired
  private VerificationTokenRepository _verificationTokenRepository;

  public User registerUser(UserDTO user) {
    User toBeSavedUser = new User();
    toBeSavedUser.setUsername(user.getUsername());
    toBeSavedUser.setPassword(_passwordEncoder.encode(user.getPassword()));
    toBeSavedUser.setEnabled(false);
    toBeSavedUser.setRole("ADMIN");
    return _userRepository.save(toBeSavedUser);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (username == null) {
      throw new UsernameNotFoundException("Username is not provided");
    }

    Optional<User> optionalUser = _userRepository.findByUsername(username);
    if (optionalUser.isEmpty()) {
      throw new UsernameNotFoundException("User with username " + username + " not found ");
    }

    User user = optionalUser.get();
    return org.springframework.security.core.userdetails.User.builder().username(user.getUsername())
        .disabled(!user.isEnabled())
        .password(user.getPassword())
        .roles(user.getRole()).build();
  }

  public void persistVerificationToken(String verificationToken, User user) {
    VerificationToken toBeSavedVerificationToken = new VerificationToken();
    toBeSavedVerificationToken.setToken(verificationToken);
    toBeSavedVerificationToken.setExpiryAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
    toBeSavedVerificationToken.setUser(user);
    _verificationTokenRepository.save(toBeSavedVerificationToken);
  }


  public User verifyRegistrationTokenAndEnableUser(String token) {

    // Check if the token is present
    // If the token is present -> Verify the expiry
    // if its expired -> Delete the verification token -> Read repair
    // Enable the user
    // Delete the token
    Optional<VerificationToken> tokenOptional = _verificationTokenRepository.findByToken(token);
    if (tokenOptional.isEmpty()) {
      throw new IllegalArgumentException("Token not valid");
    }

    VerificationToken fetchedToken = tokenOptional.get();
    if (fetchedToken.getExpiryAt().before(new Date())) {
      _verificationTokenRepository.delete(fetchedToken);
      throw new IllegalArgumentException("Token expired");
    }

    User user = fetchedToken.getUser();
    user.setEnabled(true);
    _verificationTokenRepository.delete(fetchedToken);
     return _userRepository.save(user);
  }

  public String login(String username, String password) {
    Optional<User> optionalUser = _userRepository.findByUsername(username);

    if (optionalUser.isEmpty()) {
      throw new UsernameNotFoundException("User with username" + username + " not found");
    }

    User user = optionalUser.get();
    if (!user.isEnabled()) {
      return "User not verified, please verify and relogin";
    }

    if (!_passwordEncoder.matches(password, user.getPassword())) {
      return "Invalid credentials";
    }

    // here you need to generate JWT Token
    return JwtUtil.generateJwtToken(user);
  }
}

// Login -> username, password
// check if the user is present -> i want to compare
// fetch the DB get the details of this username -> UserDetails
// Passwords match -> presented == stored value
// sessionId