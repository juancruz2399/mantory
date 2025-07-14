package com.management.app.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {

	private String upload_folder = "./src/main/resources/files/";
	private String images_folder = "./src/main/resources/static/images/";
	
	public void saveFile(MultipartFile file,Long id) throws IOException{
		
		if(!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(upload_folder +id+ file.getOriginalFilename());
			Files.write(path, bytes);
		}
	}
	public void saveImage(MultipartFile file,Long id) throws IOException{
		if(!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(images_folder +id+ file.getOriginalFilename());
			Files.write(path, bytes);
		}
	}
}
