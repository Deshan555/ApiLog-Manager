package com.selfcare.logger.v1.utils;

import org.springframework.stereotype.Component;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileNameProvider {
    private String logDirectory = "./logs/";
    public File getFileName(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date currentDate = new Date();
        String logFileName = "log_" + dateFormat.format(currentDate) + ".log";
        File logFile = new File(logDirectory + logFileName);
        return logFile;
    }
}
