package com.shyamfurniture.service.impl;

import com.shyamfurniture.exception.BadRequestException;
import com.shyamfurniture.service.FileUplodeService;
import com.shyamfurniture.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUplodeServiceImpl implements FileUplodeService {


    private Logger logger= LoggerFactory.getLogger(FileUplodeServiceImpl.class);
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
       String originalName=file.getOriginalFilename();
       String extension=originalName.substring(originalName.lastIndexOf("."));
       logger.info("FileName : {}",originalName);
       String fileName= Utils.generatedUUID(10);
       String fileNameWithExtension=fileName+extension;
       String fullPathWithExtension=path+fileNameWithExtension;
       if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") ||extension.equalsIgnoreCase(".jpeg")){

           //save file
           File folder=new File(path);

           if (!folder.exists()){
               //create the folder
               folder.mkdir();
           }
           //copy file
           Files.copy(file.getInputStream(), Paths.get(fullPathWithExtension));
           return fileNameWithExtension;
       }
       else {
           throw new BadRequestException("File with this"+extension+"not allowed !!");
       }

    }

    @Override
    public InputStream getResource(String path, String name) {
        return null;
    }
}
