package com.shyamfurniture.repositories;

import com.shyamfurniture.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,String> {



}
