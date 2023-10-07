package com.selfcare.logger.v1.tasks;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.selfcare.logger.v1.services.AzureUploader;
import com.selfcare.logger.v1.utils.DirCheck;
import com.selfcare.logger.v1.utils.FileCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private final AzureUploader azureUploader;
    private final DirCheck dirCheck;
    private final FileCheck fileCheck;
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    public ScheduledTasks(AzureUploader azureUploader, DirCheck dirCheck, FileCheck fileCheck) {
        this.azureUploader = azureUploader;
        this.dirCheck = dirCheck;
        this.fileCheck = fileCheck;
    }
    @Scheduled(fixedRate = 20000)
    public void reportCurrentTime() throws IOException {
        if(dirCheck.checkDir() && fileCheck.checkFile()){
            azureUploader.dataUpload();
        }
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}