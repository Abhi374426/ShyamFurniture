package com.shyamfurniture.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface FileUploadService {

   public String uploadFile(MultipartFile file,String path) throws IOException;

   public InputStream getResource(String path,String name);




}
