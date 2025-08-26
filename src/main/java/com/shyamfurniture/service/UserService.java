package com.shyamfurniture.service;

import com.shyamfurniture.dtos.CreateUserDto;
import com.shyamfurniture.dtos.UpdateUserDto;

import java.util.List;

public interface UserService {

    //create api
    CreateUserDto create(CreateUserDto createUserDto);

    //update api
    UpdateUserDto update(String id, UpdateUserDto updateUserDto);

    //find All
    List<CreateUserDto> getAll(int pageNumber,int pageSize);

    //find by id
    CreateUserDto findByID(String id);

    // deleted user
    String deleted(String id,String updateByy);

    //find by email

    CreateUserDto findByEmail(String email);

    //find by keyword

    List<CreateUserDto>  searchByKeyword(String keyword);


}
