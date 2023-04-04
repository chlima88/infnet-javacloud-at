package br.edu.infnet.atapi.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.amazonaws.services.s3.AmazonS3;



@Service
public class StorageService {
	
	@Autowired
	private AmazonS3 amazonS3Client;
	
	@Value("${BUCKET_NAME:cj-lab}")
	private String bucketName; 

	public String putObject(MultipartFile multipartFile) throws IllegalStateException, IOException{
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String fileName = timestamp.getTime() + "_" + multipartFile.getOriginalFilename();
		
		File targetFile = new File(fileName);		
		
		Files.copy(
				multipartFile.getInputStream(),
				Paths.get(".").resolve(fileName)
				);
		
				
	    amazonS3Client.putObject(
	            bucketName,
	            fileName,
	            targetFile);
	    
	    targetFile.delete();
	    
	    return "https://"+ this.bucketName +".s3.amazonaws.com/" + fileName;
	    
	}
	
	public void deleteObject(String bucketName, String objectName){
	    amazonS3Client.deleteObject(bucketName, objectName);
	}
}
