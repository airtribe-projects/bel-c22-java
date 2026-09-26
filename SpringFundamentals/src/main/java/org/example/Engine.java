package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


public class Engine {

  private String enginePower;

  private String engineType;

  public Engine(String enginePower, String engineType) {
    this.enginePower = enginePower;
    this.engineType = engineType;
  }

  public String getEnginePower() {
    return enginePower;
  }

  public void setEnginePower(String enginePower) {
    this.enginePower = enginePower;
  }

  public String getEngineType() {
    return engineType;
  }

  public void setEngineType(String engineType) {
    this.engineType = engineType;
  }

  public void startEngine() {
    System.out.println("Engine with engine power " + enginePower + " engine type " + engineType + " is starting ");
  }
}
