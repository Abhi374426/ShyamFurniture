package com.shyamfurniture.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface FileUplodeService {

   public String uploadFile(MultipartFile file,String path) throws IOException;

   public InputStream getResource(String path,String name);




}
