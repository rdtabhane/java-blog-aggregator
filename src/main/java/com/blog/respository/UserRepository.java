package com.blog.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.blog.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

  User findByNameAndPassword(String userName, String password);

}
