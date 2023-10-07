package com.selfcare.logger.v1.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
@Slf4j
@Component
public class DirCheck {
    private String dir = "./logs/";
    // check if directory exists or not
    public boolean checkDir() {
        System.out.println("Directory: " + dir);
        File new_directory = new File(dir);
        if(new_directory.exists()){
            log.info("Directory exists: " + new_directory.getAbsolutePath());
            return true;
        }else {
            System.out.println("Directory does not exist: " + new_directory.getAbsolutePath());
            return false;
        }
    }
    // create directory
    public void createDir() {
        try{
            File new_directory = new File(dir);
            new_directory.mkdir();
        }catch (Exception e){
            log.info("Error creating directory: " + dir);
            e.printStackTrace();
        }
    }
}
