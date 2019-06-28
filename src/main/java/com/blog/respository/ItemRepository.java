package com.blog.respository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.blog.entity.Blog;
import com.blog.entity.Item;

public interface ItemRepository extends PagingAndSortingRepository<Item, Integer> {

  List<Item> findByBlog(Blog blog, Pageable pageable);

}
