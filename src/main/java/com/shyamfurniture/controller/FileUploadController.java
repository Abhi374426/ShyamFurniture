package com.shyamfurniture.controller;

import com.shyamfurniture.dtos.ImageResponse;
import com.shyamfurniture.service.FileUploadService;
import com.shyamfurniture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/file")
public class FileUploadController {

    private final UserService userService;

    private final FileUploadService fileUploadService;
   @Value("${user.profile.image.path}")
    private String imageUploadPath;

    @Autowired
    public FileUploadController(FileUploadService fileUploadService,UserService userService){
        this.fileUploadService = fileUploadService;
        this.userService=userService;
    }
    @GetMapping("/uplodeFile/{userId}")
    public ResponseEntity<?> uplodeFile(@RequestParam("userImage") MultipartFile file, @PathVariable String userId ) throws IOException {
        String response= fileUploadService.uploadFile(file,imageUploadPath);

        userService.update(userId,)
        ImageResponse imageResponse=ImageResponse.builder().fileName(response)
                .success(true)
                .status(HttpStatus.CREATED)
                .build();
        return new ResponseEntity<>(imageResponse, HttpStatus.OK);
    }
    @GetMapping("/serverFile/{path}/{name}")
    public ResponseEntity<?> serveFile(@PathVariable String path ,@PathVariable String name){
      InputStream response=  fileUploadService.getResource(path,name);
      return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
