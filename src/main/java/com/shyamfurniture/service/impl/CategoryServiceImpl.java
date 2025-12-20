package com.shyamfurniture.service.impl;


import com.shyamfurniture.constant.AppConstant;
import com.shyamfurniture.dtos.PageableResponse;
import com.shyamfurniture.dtos.categorydtos.CategoryRequestDto;
import com.shyamfurniture.dtos.categorydtos.CategoryResponseDto;
import com.shyamfurniture.entity.Category;
import com.shyamfurniture.exception.NotFoundException;
import com.shyamfurniture.helper.Helper;
import com.shyamfurniture.repositories.CategoryRepo;
import com.shyamfurniture.service.CategoryService;
import com.shyamfurniture.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private ModelMapper modelMapper;

    private CategoryServiceImpl(CategoryRepo categoryRepo, ModelMapper modelMapper) {
        this.categoryRepo = categoryRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryResponseDto create(CategoryRequestDto categoryRequestDto) {
        //create unique category id
        String id = "CAT-" + Utils.generatedUUID(5);
        Category category = modelMapper.map(categoryRequestDto, Category.class);
        log.info("This is category object before modified: {}", category);
        //here set custom id
        category.setCategoryId(id);
        //here the save entity
        Category saveCategory = categoryRepo.save(category);
        return modelMapper.map(saveCategory, CategoryResponseDto.class);
    }

    @Override
    public CategoryResponseDto update(CategoryRequestDto categoryRequestDto,String categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(
                        String.format(AppConstant.NOT_FOUND, "Category", categoryId)
                ));
        log.info("this is category old object:{}",category);
        modelMapper.map(categoryRequestDto,category);
        log.info("this is after update category obj:{}",category);
        Category saveObj=   categoryRepo.save(category);

        return modelMapper.map(saveObj,CategoryResponseDto.class);
    }

    @Override
    public PageableResponse<CategoryResponseDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc")) ?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
              Page<Category> page= categoryRepo.findAll(pageable);
              PageableResponse<CategoryResponseDto> response= Helper.getPageableResponse(page,CategoryResponseDto.class);

        return response;
    }

    @Override
    public String delete(String categoryId) {
          categoryRepo.findById(categoryId).orElseThrow(()->new NotFoundException(String.format(AppConstant.NOT_FOUND,"Category",categoryId)));
          categoryRepo.deleteById(categoryId);
        return "category successfully deleted !";
    }

    @Override
    public void updateCategoryImageName(String imageName , String categoryId) {
     Category category=    categoryRepo.findById(categoryId).orElseThrow(()->new NotFoundException(String.format(AppConstant.NOT_FOUND,"Category",categoryId)));
     category.setCoverImage(imageName);
     categoryRepo.save(category);
    }

    @Override
    public CategoryResponseDto getById(String categoryId) {
     Category category=   categoryRepo.findById(categoryId).orElseThrow(()->new NotFoundException(String.format(AppConstant.NOT_FOUND,"Category",categoryId)));
     return  modelMapper.map(category,CategoryResponseDto.class);
    }

}
