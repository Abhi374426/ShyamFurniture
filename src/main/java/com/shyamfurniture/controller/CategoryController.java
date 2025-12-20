package com.shyamfurniture.controller;

import com.shyamfurniture.dtos.ApiResponseaMessage;
import com.shyamfurniture.dtos.ImageResponse;
import com.shyamfurniture.dtos.PageableResponse;
import com.shyamfurniture.dtos.categorydtos.CategoryRequestDto;
import com.shyamfurniture.dtos.categorydtos.CategoryResponseDto;
import com.shyamfurniture.entity.Category;
import com.shyamfurniture.service.CategoryService;
import com.shyamfurniture.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    private final FileUploadService fileUploadService;

    @Value("${category.image.path}")
    private String categoryImageUploadPath;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CategoryRequestDto categoryRequestDto){
       CategoryResponseDto  responseDto= categoryService.create(categoryRequestDto);
     ApiResponseaMessage<CategoryResponseDto> responseaMessage=   ApiResponseaMessage.<CategoryResponseDto>builder()
                .message("CREATED")
                .success(true)
                .status(HttpStatus.CREATED)
                .data(responseDto)
                .build();
     return new ResponseEntity<>(responseaMessage,HttpStatus.CREATED);
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<?> update(@RequestBody CategoryRequestDto categoryRequestDto, @PathVariable String categoryId){
        CategoryResponseDto categoryResponseDto=     categoryService.update(categoryRequestDto,categoryId);
      ApiResponseaMessage<CategoryResponseDto> responseaMessage=  ApiResponseaMessage.<CategoryResponseDto>builder()
                .message("CREATED")
                .success(true)
                .status(HttpStatus.OK)
                .data(categoryResponseDto)
                .build();
        return new ResponseEntity<>(responseaMessage,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
                                    @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
                                    @RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
                                    @RequestParam(value ="sortDir",defaultValue = "asc",required = false) String sortDir){

         PageableResponse<CategoryResponseDto> response= categoryService.getAll(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PostMapping("/uploadFile/{categoryId}")
    public ResponseEntity<?> uploadFile(@RequestParam("userImage")MultipartFile file,@PathVariable String categoryId) throws IOException {
      String fileName= fileUploadService.uploadFile(file,categoryImageUploadPath);
                   categoryService.updateCategoryImageName(fileName,categoryId);
      ImageResponse imageResponse=  ImageResponse.builder().fileName(fileName)
                .success(true)
                .status(HttpStatus.CREATED)
                .build();
        return new ResponseEntity<>(imageResponse,HttpStatus.OK);

    }
    @GetMapping("/serverFile/{categoryId}")
    public ResponseEntity<?> serverCategoryImages(@PathVariable String categoryId) throws FileNotFoundException {
         CategoryResponseDto responseDto=  categoryService.getById(categoryId);
      String imageName=   responseDto.getCoverImage();
        log.info("Category image Name:{}",imageName);
       InputStream inputStream= fileUploadService.getResource(categoryImageUploadPath,imageName);
       if (inputStream==null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
        Resource resource=new InputStreamResource(inputStream);
        MediaType mediaType=MediaType.IMAGE_JPEG;
        if (imageName.toLowerCase().endsWith(".png")){
            mediaType=MediaType.IMAGE_PNG;
        }
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);
    }

}
