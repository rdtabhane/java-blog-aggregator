package com.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IndexController {

  private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
  
  

  @GetMapping("/index")
  public String index() {
    logger.info("*****index controller****");
    return "Hello";
  }

}
