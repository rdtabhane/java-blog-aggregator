package com.blog.respository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.blog.entity.Blog;
import com.blog.entity.User;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

  List<Blog> findByUser(User user);

}
