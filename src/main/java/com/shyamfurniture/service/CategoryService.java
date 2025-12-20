package com.shyamfurniture.service;

import com.shyamfurniture.dtos.PageableResponse;
import com.shyamfurniture.dtos.categorydtos.CategoryRequestDto;
import com.shyamfurniture.dtos.categorydtos.CategoryResponseDto;
import com.shyamfurniture.entity.Category;

import java.util.List;

public interface CategoryService {

    //create
    public CategoryResponseDto create(CategoryRequestDto categoryRequestDto);
    //update
    public CategoryResponseDto update(CategoryRequestDto categoryRequestDto,String category);
    //getAll
    public PageableResponse<CategoryResponseDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);
    //deleted
    public String  delete(String categoryId);

    public void updateCategoryImageName( String imageName,String categoryId);

    CategoryResponseDto getById(String categoryId);
}
