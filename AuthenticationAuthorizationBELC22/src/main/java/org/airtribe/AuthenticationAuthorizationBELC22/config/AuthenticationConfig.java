package org.airtribe.AuthenticationAuthorizationBELC22.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class AuthenticationConfig {
  @Bean
  public PasswordEncoder _passwordEncoder() {
    return new BCryptPasswordEncoder(11);
  }

  @Bean
  public SecurityFilterChain httpSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
            .requestMatchers("/signup", "/verifyRegistration", "/signin", "/helloWorld")
            .permitAll()
            .anyRequest()
            .authenticated())
        .formLogin(formLogin -> formLogin.defaultSuccessUrl("/", true).permitAll());
    return httpSecurity.build();
  }
}

