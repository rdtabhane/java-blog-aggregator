package com.blog.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "title", "description", "publishedDate", "link"})
public class ItemDto {

  @JsonProperty("id")
  public Integer id;
  @JsonProperty("title")
  public String title;
  @JsonProperty("description")
  public Object description;
  @JsonProperty("publishedDate")
  public Date publishedDate;
  @JsonProperty("link")
  public String link;

}
