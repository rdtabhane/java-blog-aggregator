package com.blog.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "url", "name", "items"})
public class BlogDto {

  @JsonProperty("id")
  public Integer id;
  @JsonProperty("url")
  public String url;
  @JsonProperty("name")
  public String name;
  @JsonProperty("items")
  public List<ItemDto> items = null;

}
