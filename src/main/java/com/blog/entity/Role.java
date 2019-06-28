package com.blog.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "new_role")
@Entity
@Getter
@Setter
public class Role implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_generator")
  @SequenceGenerator(name = "role_generator", sequenceName = "role_seq")
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "name")
  private String name;

  @ManyToMany(mappedBy = "roles")
  private List<User> users;
}
