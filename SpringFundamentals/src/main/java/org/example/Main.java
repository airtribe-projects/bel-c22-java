package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
  public static void main(String[] args) {

    // Spring Container
    // IoC
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
    // Spring -> IoC & DI
    Car beanCar = applicationContext.getBean(Car.class);
    Car beanCar2 = applicationContext.getBean(Car.class);
    //Engine engine = applicationContext.getBean(Engine.class);
    /// Singleton in nature by default
    beanCar.startCar();
    beanCar2.startCar();
//    engine.startEngine();

    System.out.println(beanCar.hashCode());
    System.out.println(beanCar2.hashCode());

  }
}

// Three methods to create the beans
// XML Configuration
// Java based bean configuration
// Annotation based bean configuration -> Spring boot

// Automatic bean creation + auto wiring
// Primary
// Qualifier
// profiles