package com.shyamfurniture.controller;

import com.shyamfurniture.dtos.ApiResponseaMessage;
import com.shyamfurniture.dtos.CreateUserDto;
import com.shyamfurniture.dtos.PageableResponse;
import com.shyamfurniture.dtos.UpdateUserDto;
import com.shyamfurniture.exception.BadRequestException;
import com.shyamfurniture.service.UserService;
//import jakarta.validation.Valid;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CreateUserDto createUserDto){
     CreateUserDto createUserDto1 =  userService.create(createUserDto);
        ApiResponseaMessage<CreateUserDto> apiResponseaMessage = ApiResponseaMessage.<CreateUserDto>builder()
                .message("CREATED")
                .success(true)
                .status(HttpStatus.CREATED)
                .data(createUserDto1)
                .build();

        return new ResponseEntity<>(apiResponseaMessage,HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam String id,@Valid @RequestBody UpdateUserDto updateUserDto){
      UpdateUserDto createUserDto1 =   userService.update(id,updateUserDto);
        ApiResponseaMessage<UpdateUserDto> apiResponseaMessage= ApiResponseaMessage.<UpdateUserDto>builder()
                .message("UPDATED")
                .success(true)
                .status(HttpStatus.OK)
                .data(createUserDto1)
                .build();

        return new ResponseEntity<>(apiResponseaMessage,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber
            ,@RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
                                    @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
                                    @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir) {
        PageableResponse<CreateUserDto> createUserDto =userService.getAll(pageNumber,pageSize,sortBy,sortDir);


        return new ResponseEntity<>(createUserDto,HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        CreateUserDto createUserDto =   userService.findByID(id);
        ApiResponseaMessage<CreateUserDto> apiResponseaMessage= ApiResponseaMessage.<CreateUserDto>builder()
                .message("SUCCESS")
                .success(true)
                .status(HttpStatus.OK)
                .data(createUserDto)
                .build();

        return new ResponseEntity<>(apiResponseaMessage,HttpStatus.OK);
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable String email){
        CreateUserDto createUserDto =   userService.findByEmail(email);
        ApiResponseaMessage<CreateUserDto> apiResponseaMessage= ApiResponseaMessage.<CreateUserDto>builder()
                .message("SUCCESS")
                .success(true)
                .status(HttpStatus.OK)
                .data(createUserDto)
                .build();


        return new ResponseEntity<>(apiResponseaMessage,HttpStatus.OK);
    }
    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchByKeyword(@PathVariable String keyword){
        List<CreateUserDto> createUserDto =userService.searchByKeyword(keyword);
        ApiResponseaMessage<List<CreateUserDto>> responseaMessage=ApiResponseaMessage.<List<CreateUserDto>>builder()
                .message("SUCCESS")
                .success(true)
                .status(HttpStatus.OK)
                .data(createUserDto)
                .build();


        return new ResponseEntity<>(responseaMessage,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletedById(@PathVariable String id,@RequestBody Map<String,String> requestBody){
        String updateBy= requestBody.get("updateBy");
        if (updateBy.isEmpty() ||updateBy==null){
                throw new BadRequestException("updateBy is required!!");
        }
        String msg=userService.deleted(id,updateBy);
        ApiResponseaMessage<String> responseaMessage=ApiResponseaMessage.<String>builder()
                .message("success")
                .success(true)
                .status(HttpStatus.OK)
                .data(msg)
                .build();


        return new ResponseEntity<>(responseaMessage,HttpStatus.OK);
    }


}
