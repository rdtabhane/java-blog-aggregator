package com.blog.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "item")
@Data
public class Item implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 8963817027530803585L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_generator")
  @SequenceGenerator(name="item_generator", sequenceName = "item_seq")
  @Column(name = "id", nullable = false)
  private Integer id;
  
  @Column(name="title")
  private String title;
  
  @Column(name="description")
  private String description;

  @Column(name="published_date")
  private Date publishedDate;
  
  @Column(name="link")
  private String link;
  
  @ManyToOne
  @JoinColumn(name = "blog_id")
  private Blog blog;
}
