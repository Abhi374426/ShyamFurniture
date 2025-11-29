package com.shyamfurniture.controller;

import com.shyamfurniture.dtos.CreateUserDto;
import com.shyamfurniture.dtos.ImageResponse;
import com.shyamfurniture.dtos.UpdateUserDto;
import com.shyamfurniture.service.FileUploadService;
import com.shyamfurniture.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/file")
public class FileUploadController {

    private final UserService userService;

    private final FileUploadService fileUploadService;
   @Value("${user.profile.image.path}")
    private String imageUploadPath;

   Logger logger= LoggerFactory.getLogger(FileUploadService.class);

    @Autowired
    public FileUploadController(FileUploadService fileUploadService,UserService userService){
        this.fileUploadService = fileUploadService;
        this.userService=userService;
    }
    @GetMapping("/uplodeFile/{userId}")
    public ResponseEntity<?> uplodeFile(@RequestParam("userImage") MultipartFile file, @PathVariable String userId ) throws IOException {
        String response= fileUploadService.uploadFile(file,imageUploadPath);
        UpdateUserDto user=UpdateUserDto.builder()
                .imageName(response)
                .updateBy("admin")
                .build();
        userService.update(userId,user);
        ImageResponse imageResponse=ImageResponse.builder().fileName(response)
                .success(true)
                .status(HttpStatus.CREATED)
                .build();
        return new ResponseEntity<>(imageResponse, HttpStatus.OK);
    }
    @GetMapping("/serverFile/{path}/{name}")
    public ResponseEntity<?> serveFile(@PathVariable String path ,@PathVariable String name) throws FileNotFoundException {
      InputStream response=  fileUploadService.getResource(path,name);
      return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //serve file
    @GetMapping("/image/{userId}")
    public ResponseEntity<?> serveImage(@PathVariable String userId) throws IOException {
        CreateUserDto userDto=     userService.findByID(userId);
        logger.info("User image name : {}",userDto.getImageName());
     InputStream inputStream=   fileUploadService.getResource(imageUploadPath,userDto.getImageName());
        if (inputStream == null) {
            // agar file null return hui (not found), to 404 bhej do
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Resource resource=new InputStreamResource(inputStream);
        MediaType mediaType=MediaType.IMAGE_JPEG;
        if (userDto.getImageName().toLowerCase().endsWith(".png")){
            mediaType=MediaType.IMAGE_PNG;
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);

    }

}
