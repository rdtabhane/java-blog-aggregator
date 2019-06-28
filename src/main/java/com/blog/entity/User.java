package com.blog.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 8091197205740959431L;
  
  @Id
  //@GeneratedValue
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
  @SequenceGenerator(name="user_generator", sequenceName = "user_seq")
  @Column(name = "id", nullable = false)
  private Integer id;
  
  @Column(name = "name")
  private String name;
  
  @Column(name = "password")
  private String password;
  
  @Column(name = "email")
  private String email;
  
  @ManyToMany
  @JoinTable
  private List<Role> roles;
  
  @OneToMany(mappedBy = "user")
  private List<Blog> blogs;

}
