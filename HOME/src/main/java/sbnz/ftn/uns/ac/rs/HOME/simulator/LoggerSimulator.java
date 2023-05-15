package sbnz.ftn.uns.ac.rs.HOME.simulator;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
@EnableScheduling
@Transactional
public class LoggerSimulator {

    String admin_path = Paths.get("").normalize().toAbsolutePath().toString();

    @Autowired
    private SimpMessagingTemplate template;

    @Scheduled(fixedRate = 5000)
    public void sentLogs(){

        try (BufferedReader br = new BufferedReader(new FileReader(admin_path+ "/home.log"))) {

            String line;

            while ((line = br.readLine()) != null) {

                final String logLine = line;

                if(!logLine.contains("HOME-APP")){
                    continue;
                }

                String escapedLogLine = new JSONObject().put("message", logLine).toString();

                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//                System.out.println(escapedLogLine);
                executor.execute(() -> template.convertAndSend("/topic/logs", escapedLogLine));

            }
            br.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
