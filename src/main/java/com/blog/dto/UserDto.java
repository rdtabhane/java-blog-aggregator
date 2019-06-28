package com.blog.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name", "password", "email", "roles", "blogs"})
public class UserDto {

  @JsonProperty("id")
  public Integer id;

  @JsonProperty("name")
  @NotNull(message = "Name not null")
  @NotBlank(message = "Name not blank")
  public String name;

  @JsonProperty("password")
  @NotNull(message = "Password not null")
  @NotBlank(message = "Password not blank")
  public String password;

  @JsonProperty("email")
  @NotNull(message = "Email not null")
  @NotBlank(message = "Email not blank")
  public String email;

  @JsonProperty("roles")
  public List<RoleDto> roles = null;

  @JsonProperty("blogs")
  public List<BlogDto> blogs = null;
}
