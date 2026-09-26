package org.example;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("dieselEngine")
public class DieselEngine extends Engine{
  public DieselEngine(@Value("${dieselEngine.enginePower}") String enginePower, @Value("${dieselEngine.engineType}") String engineType) {
    super(enginePower, engineType);
  }
}
