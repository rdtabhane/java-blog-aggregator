package com.blog.servcie.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.blog.dto.BlogDto;
import com.blog.dto.ItemDto;
import com.blog.dto.RoleDto;
import com.blog.dto.UserDto;
import com.blog.entity.Blog;
import com.blog.entity.Item;
import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.respository.BlogRepository;
import com.blog.respository.ItemRepository;
import com.blog.respository.UserRepository;
import com.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BlogRepository blogRepository;

  @Autowired
  private ItemRepository itemRepository;

  @Value("${app.blog.pagination.start}")
  private String pageStart;

  @Value("${app.blog.pagination.end}")
  private String pageEnd;

  @Value("${app.blog.pagination.sorting}")
  private String pageSort;

  @Override
  public User login(String userName, String password) {
    logger.info("In login Service impl");
    User findByUserNameAndPassword = userRepository.findByNameAndPassword(userName, password);
    if (null != findByUserNameAndPassword) {
      return findByUserNameAndPassword;
    }
    return null;
  }

  @Override
  @Transactional
  public UserDto findOne(Integer id) {
    logger.info("In login Service impl findone method");
    Optional<User> userOpt = userRepository.findById(id);
    if (userOpt.isPresent()) {
      User user = userOpt.get();
      List<Blog> blogs = blogRepository.findByUser(user);
      for (Blog blog : blogs) {
        List<Item> items = itemRepository.findByBlog(blog, PageRequest
            .of(Integer.valueOf(pageStart), Integer.valueOf(pageEnd), Direction.DESC, pageSort));
        blog.setItems(items);
      }
      user.setBlogs(blogs);

      UserDto userMapper = userMapper(user);

      return userMapper;
    }
    return null;
  }

  private UserDto userMapper(User user) {
    UserDto userDto = new UserDto();
    userDto.setEmail(user.getEmail());
    userDto.setId(user.getId());
    userDto.setName(user.getName());
    userDto.setPassword(user.getPassword());

    // user details mapper
    List<ItemDto> itemDtos = new ArrayList<>();
    List<BlogDto> blogDtos = new ArrayList<BlogDto>();
    List<Blog> blogs = user.getBlogs();
    for (Blog blog : blogs) {
      BlogDto blogDto = new BlogDto();
      blogDto.setId(blog.getId());
      blogDto.setName(blog.getName());
      blogDto.setUrl(blog.getUrl());
      List<Item> items = blog.getItems();
      for (Item item : items) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setLink(item.getLink());
        itemDto.setTitle(item.getTitle());
        itemDto.setDescription(item.getDescription());
        itemDto.setPublishedDate(item.getPublishedDate());
        itemDtos.add(itemDto);
      }
      blogDto.setItems(itemDtos);
      blogDtos.add(blogDto);
    }
    userDto.setBlogs(blogDtos);

    // Role mapper
    List<RoleDto> roleDtos = new ArrayList<>();
    List<Role> roles = user.getRoles();
    for (Role role : roles) {
      RoleDto roleDto = new RoleDto();
      roleDto.setId(role.getId());
      roleDto.setName(role.getName());
      roleDtos.add(roleDto);
    }
    userDto.setRoles(roleDtos);

    return userDto;
  }

  @Override
  public User userRegister(UserDto userDto) {
    logger.info("In login Service impl userRegister method");
    User user = getUserDetails(userDto);
    User saveUser = userRepository.save(user);
    if (null != saveUser) {
      return saveUser;
    }
    return null;
  }

  private User getUserDetails(UserDto userDto) {
    User user = new User();
    user.setName(userDto.getName());
    user.setPassword(userDto.getPassword());
    user.setEmail(userDto.getEmail());
    return user;
  }

}
