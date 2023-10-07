package com.selfcare.logger.v1.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Slf4j
@Component
public class FileCheck {
    // check is log file is present or not in the logs directory
    public boolean checkFile() {
        File logFile = new FileNameProvider().getFileName();
        if(logFile.exists()){
            log.info("Log file exists: " + logFile.getAbsolutePath());
            return true;
        }else {
            log.info("Log file does not exist: " + logFile.getAbsolutePath());
            return false;
        }
    }
    // create log file in the logs directory
    public void CreateFile(){
        File logFile = new FileNameProvider().getFileName();
        try{
            log.info("Creating log file: " + logFile.getAbsolutePath());
            logFile.createNewFile();
        }catch (IOException e){
            log.info("Error creating log file: " + logFile.getAbsolutePath());
            e.printStackTrace();
        }
    }
}
