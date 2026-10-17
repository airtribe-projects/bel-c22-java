package org.airtribe.AuthenticationAuthorizationBELC22;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


@SpringBootApplication
@EnableMethodSecurity
public class AuthenticationAuthorizationBelc22Application {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationAuthorizationBelc22Application.class, args);
	}

}
