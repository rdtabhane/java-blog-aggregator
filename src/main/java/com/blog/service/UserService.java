package com.blog.service;

import javax.validation.Valid;
import com.blog.dto.UserDto;
import com.blog.entity.User;

public interface UserService {

  public User login(String userName, String password);

  public UserDto findOne(Integer id);

  public User userRegister(@Valid UserDto userDto);
}
