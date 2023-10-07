package com.selfcare.logger.v1.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class AzureUploader {
    @Autowired
    BlobServiceClient blobServiceClient;
    @Autowired
    BlobContainerClient blobContainerClient;

    // that function upload the file to azure blob storage
    public void dataUpload() throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date currentDate = new Date();
        String logFileName = "log_" + dateFormat.format(currentDate) + ".log";
        String filePath = "logs\\"+logFileName;
        MultipartFile multipartFile = convertFileToMultipartFile(filePath);
        try{
            BlobClient blob = blobContainerClient.getBlobClient(multipartFile.getOriginalFilename());
            blob.upload(multipartFile.getInputStream(), multipartFile.getSize(), true);
            System.out.println("File Uploaded");
            System.out.println(blob.getBlobUrl());
        }catch (Exception error){
            System.out.println(error);
            log.info("Error uploading file to azure blob storage: " + error);
        }
    }
    // that function convert the file to multipart file
    public static MultipartFile convertFileToMultipartFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found at path: " + filePath);
        }
        // Read the file content into a byte array
        byte[] fileContent = new byte[(int) file.length()];
        try (FileInputStream inputStream = new FileInputStream(file)) {
            inputStream.read(fileContent);}
        // Create a MockMultipartFile
        MultipartFile multipartFile = new MockMultipartFile(
                file.getName(),
                file.getName(),
                null,
                fileContent
        );
        return multipartFile;
    }
}