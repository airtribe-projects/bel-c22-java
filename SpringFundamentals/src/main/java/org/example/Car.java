package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class Car {
  @Value("${car.name}")
  private String name;

  @Value("${car.model}")
  private String model;

  @Value("${car.carType}")
  private String carType;

  // Loose coupling
  @Autowired
  private Engine engine;

  public Car(@Value("${car.name}") String name, @Value("${car.model}") String model, @Value("${car.carType}") String carType, Engine engine) {
    this.name = name;
    this.model = model;
    this.carType = carType;
    this.engine = engine;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getCarType() {
    return carType;
  }

  public void setCarType(String carType) {
    this.carType = carType;
  }

  public Engine getEngine() {
    return engine;
  }

  public void setEngine(Engine engine) {
    this.engine = engine;
  }

  public void startCar() {
     this.engine.startEngine();
    System.out.println("Car Name " + name + " Car type " + carType + " Car model " + model + " is starting");
  }
}
