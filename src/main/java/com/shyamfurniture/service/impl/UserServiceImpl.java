package com.shyamfurniture.service.impl;

import com.shyamfurniture.dtos.CreateUserDto;
import com.shyamfurniture.dtos.PageableResponse;
import com.shyamfurniture.dtos.UpdateUserDto;
import com.shyamfurniture.entity.User;
import com.shyamfurniture.exception.BadRequestException;
import com.shyamfurniture.exception.NotFoundException;
import com.shyamfurniture.exception.ResourceAlreadyExistsException;
import com.shyamfurniture.helper.Helper;
import com.shyamfurniture.repositories.UserRepo;
import com.shyamfurniture.service.UserService;
import com.shyamfurniture.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${user.profile.image.path}")
    private String fullImagePath;


    @Override
    public CreateUserDto create(CreateUserDto createUserDto) {

        Optional<User> userAlreadyExist = userRepo.findByEmail(createUserDto.getEmail());
        if (userAlreadyExist.isPresent()) {
            throw new ResourceAlreadyExistsException("User with email " + createUserDto.getEmail() + " already exists.");
        }
        String id = Utils.generatedUUID(5);
        createUserDto.setId(id);
        User user = modelMapper.map(createUserDto, User.class);
        System.out.println(user);
        user.setCreateAt(LocalDateTime.now());
        user.setUpdateBy(null);
        User user1 = userRepo.save(user);
        return modelMapper.map(user1, CreateUserDto.class);
    }

    @Override
    public UpdateUserDto update(String id, UpdateUserDto updateUserDto) {
        User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("User not found with give id !!"));
        if (updateUserDto.getUpdateBy() == null || updateUserDto.getUpdateBy().isEmpty()) {
            throw new BadRequestException("update by can not be empty !!");
        }
        Optional<User> userAlreadyExist = userRepo.findByEmail(updateUserDto.getEmail());
        if (userAlreadyExist.isPresent()) {
            throw new ResourceAlreadyExistsException("User with email " + updateUserDto.getEmail() + " already exists.");
        }
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(updateUserDto, user);
        user.setUpdateAt(LocalDateTime.now());
        User updateUser = userRepo.save(user);
        return modelMapper.map(updateUser, UpdateUserDto.class);
    }

    @Override
    public PageableResponse<CreateUserDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc")) ?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending()) ;
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<User> pageUsers = userRepo.findAll(pageable);
       PageableResponse<CreateUserDto> response= Helper.getPageableResponse(pageUsers,CreateUserDto.class);
        return response;
    }

    @Override
    public CreateUserDto findByID(String id) {
        User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("User not found with given id !!"));
        return modelMapper.map(user, CreateUserDto.class);
    }

    @Override
    public String deleted(String id,String updateBy){
       User user= userRepo.findById(id).orElseThrow(() -> new NotFoundException("User not found with given id !!"));

       String fullPath=fullImagePath+user.getImageName();
     try {
         Path path= Paths.get(fullPath);
         Files.delete(path);
     }
     catch (NoSuchFileException ex){
     log.info("User image is not found in folder ");
     ex.printStackTrace();
     }
     catch (IOException ex){
      ex.printStackTrace();
     }


        user.setDeleted(true);
        user.setUpdateBy(updateBy);
        user.setUpdateAt(LocalDateTime.now());

        userRepo.save(user);

        return "User successfully deleted!";
    }

    @Override
    public CreateUserDto findByEmail(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("Data not found with this email !! "));
        return modelMapper.map(user, CreateUserDto.class);
    }

    @Override
    public List<CreateUserDto> searchByKeyword(String keyword) {
        List<User> userList = userRepo.findByNameContaining(keyword);
        if (userList.isEmpty()) {
            throw new NotFoundException("data is not found !!");
        }
        return userList.stream()
                .map(user -> modelMapper.map(user, CreateUserDto.class))
                .toList();
    }
}
