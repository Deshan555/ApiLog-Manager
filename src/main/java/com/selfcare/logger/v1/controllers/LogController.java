package com.selfcare.logger.v1.controllers;

import com.selfcare.logger.v1.services.AzureUploader;
import com.selfcare.logger.v1.services.LogFileWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/logger/v1")
public class LogController {
    private final LogFileWriter logFileWriter;
    private final AzureUploader azureUploader;
    public LogController(LogFileWriter logFileWriter, AzureUploader azureUploader) {
        this.logFileWriter = logFileWriter;
        this.azureUploader = azureUploader;
    }
    @GetMapping("/demo")
    public String handleRequest(HttpServletRequest request) {
        try {
            String clientIP = request.getRemoteAddr();
            int clientPort = request.getRemotePort();
            String serverIP = request.getLocalAddr();
            int serverPort = request.getLocalPort();
            String protocol = request.getProtocol();
            String method = request.getMethod();
            String url = request.getRequestURL().toString();
            int status = HttpServletResponse.SC_OK; // You'll need to track this separately
            String reason = "OK"; // You'll need to track this separately
            // You can also add logic to extract a "rule" if necessary
            System.out.println("Client IP: " + clientIP);
            System.out.println("Client Port: " + clientPort);
            System.out.println("Server IP: " + serverIP);
            System.out.println("Server Port: " + serverPort);
            System.out.println("Protocol: " + protocol);
            System.out.println("Method: " + method);
            System.out.println("URL: " + url);
            System.out.println("Status: " + status);
            System.out.println("Reason: " + reason);
            try{
                logFileWriter.logDemon(request, HttpServletResponse.SC_OK, reason);
            } catch (Exception e) {
                logFileWriter.logDemon(request, HttpServletResponse.SC_BAD_GATEWAY, reason);
                System.out.println("Rule: No rule found: " + e);
            }
            return "Your response here";
        } catch (Exception e) {
            logFileWriter.logDemon(request, HttpServletResponse.SC_BAD_GATEWAY, String.valueOf(e));
            return "Failure";
        }
    }
    @GetMapping("/log")
    public String uploadFile(HttpServletRequest request){
        try{
            azureUploader.dataUpload();
            logFileWriter.logDemon(request,
                    HttpServletResponse.SC_BAD_GATEWAY,
                    "log file Upload Successfully");
            return "File Uploaded";
        }catch(Exception error){
            System.out.println(error);
            logFileWriter.logDemon(request,
                    HttpServletResponse.SC_BAD_REQUEST,
                    "log file upload Failed");
            return "File Upload Failed : ";
        }
    }
}

