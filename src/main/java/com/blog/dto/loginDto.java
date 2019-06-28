package com.blog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class loginDto {

  @NotNull(message = "UserName not null")
  @NotBlank(message = "UserName not blank")
  private String userName;
  @NotNull(message = "Password not null")
  @NotBlank(message = "password not blank")
  private String password;
}
