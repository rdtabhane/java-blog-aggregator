package com.blog.controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.blog.dto.UserDto;
import com.blog.dto.loginDto;
import com.blog.entity.User;
import com.blog.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService loginService;

  @PostMapping("/login")
  public ResponseEntity<User> loginUser(@Valid @RequestBody loginDto loginDto) {
    logger.info("*****loginProfile controller****");
    User login = loginService.login(loginDto.getUserName(), loginDto.getPassword());
    if (null != login) {
      return new ResponseEntity<>(login, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDto> userDetails(@RequestParam Integer id) {
    logger.info("*****loginProfile controller****");
    UserDto user = loginService.findOne(id);
    if (null != user) {
      return new ResponseEntity<UserDto>(user, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }
  
  @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> userRegister(@Valid @RequestBody UserDto userDto) {
    logger.info("*****loginProfile controller userRegister method****");
    User user = loginService.userRegister(userDto);
    if (null != user) {
      return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }
}
