package org.airtribe.LearnerManagementSystemBelC22.controller;

import org.airtribe.LearnerManagementSystemBelC22.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController {

  private HelloWorldService _helloWorldService;

  public HelloWorldController(HelloWorldService helloWorldService) {
    this._helloWorldService = helloWorldService;
  }

  @GetMapping("/hello")
  public String helloWorld() {
    return _helloWorldService.helloWorld();
  }
}
