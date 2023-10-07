package com.selfcare.logger.v1.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import com.selfcare.logger.v1.utils.DirCheck;
import com.selfcare.logger.v1.utils.FileCheck;
@Slf4j
@Component
public class LogFileWriter {
    private static String logDirectory = "./logs/";
    private static DirCheck dirCheck = null;
    private static FileCheck fileCheck = null;
    public LogFileWriter(DirCheck dirCheck, FileCheck fileCheck) {
        this.dirCheck = dirCheck;
        this.fileCheck = fileCheck;
    }

    public static void logDemon(HttpServletRequest request, int response, String reason){
        if(dirCheck.checkDir()) {
            if (fileCheck.checkFile()) {
                writeLog(request, response, reason);
            } else {
                log.info("Log file does not exist");
                fileCheck.CreateFile();
                writeLog(request, response, reason);
            }
        }else{
            log.info("Directory does not exist");
            dirCheck.createDir();
            fileCheck.CreateFile();
            writeLog(request, response, reason);
        }
    }
    public static void writeLog(HttpServletRequest request, int status, String reason){
        String log = getString(request, status, reason);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date currentDate = new Date();
        String logFileName = "log_" + dateFormat.format(currentDate) + ".log";
        File logFile = new File(logDirectory + logFileName);

        try{
            FileWriter logWriter = new FileWriter(logFile, true);
            logWriter.write(log + "\n");
            logWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static String getString(HttpServletRequest request, int status, String reason) {
        String date = getDate();
        String time = getTime();
        String log = date + " " + time + " " + request.getRemoteAddr() + " " +
                request.getRemotePort() + " " + request.getLocalAddr() + " " +
                request.getLocalPort() + " " + request.getProtocol() + " " +
                request.getMethod() + " " + request.getRequestURI() + " " +
                request.getProtocol() + " " + request.getRemoteHost() + " " +
                status + " " + reason;
        return log;
    }
    public static String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime currentTime = LocalTime.now();
        String formattedTime = currentTime.format(formatter);
        return formattedTime;
    }
    public static String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }

}