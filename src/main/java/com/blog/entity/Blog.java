package com.blog.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "blog")
@Data
public class Blog implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = -7348161719989480720L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_generator")
  @SequenceGenerator(name="blog_generator", sequenceName = "blog_seq")
  @Column(name = "id", nullable = false)
  private Integer id;
  
  @Column(name = "url")
  private String url;
  
  @Column(name = "name")
  private String name;
  
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  
  @OneToMany(mappedBy = "blog")
  private List<Item> items;
  
}
