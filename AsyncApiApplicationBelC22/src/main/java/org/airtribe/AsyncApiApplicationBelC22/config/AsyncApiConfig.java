package org.airtribe.AsyncApiApplicationBelC22.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class AsyncApiConfig {
  @Bean
  public RestTemplate _restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public WebClient _webClient() {
    return WebClient.builder().build();
  }
}
