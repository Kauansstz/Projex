package com.kauan.projex.repository;


import java.util.List;

import com.kauan.projex.model.CategoryModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kauan.projex.utils.Category;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long>{
    List<CategoryModel> findByCategory(Category categoria);
}
