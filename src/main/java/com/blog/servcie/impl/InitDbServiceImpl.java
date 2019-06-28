package com.blog.servcie.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.blog.entity.Blog;
import com.blog.entity.Item;
import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.respository.BlogRepository;
import com.blog.respository.ItemRepository;
import com.blog.respository.RoleRepository;
import com.blog.respository.UserRepository;

@Service
@Transactional
public class InitDbServiceImpl {

  public static final Logger logger = LoggerFactory.getLogger(InitDbServiceImpl.class);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BlogRepository blogRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private ItemRepository itemRepository;

  @PostConstruct
  public void init() {
    Role roleUser = new Role();
    roleUser.setName("ROLE_USER");
    roleRepository.save(roleUser);

    Role roleAdmin = new Role();
    roleAdmin.setName("ROLE_ADMIN");
    roleRepository.save(roleAdmin);

    User userAdmin = new User();
    userAdmin.setName("admin");
    List<Role> roles = new ArrayList<>();
    roles.add(roleUser);
    roles.add(roleAdmin);
    userAdmin.setRoles(roles);
    userRepository.save(userAdmin);

    Blog blog = new Blog();
    blog.setName("Baeldung");
    blog.setUrl("https://www.baeldung.com/java-8-lambda-expressions-tips");
    blog.setUser(userAdmin);
    blogRepository.save(blog);

    Item item1 = new Item();
    item1.setBlog(blog);
    item1.setTitle("first");
    item1.setLink("https://www.baeldung.com");
    item1.setPublishedDate(new Date());
    itemRepository.save(item1);

    Item item2 = new Item();
    item2.setBlog(blog);
    item2.setTitle("first");
    item2.setLink("https://www.baeldung.com");
    item2.setPublishedDate(new Date());
    itemRepository.save(item2);
  }
}
