package sbnz.ftn.uns.ac.rs.HOME.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sbnz.ftn.uns.ac.rs.HOME.model.Message;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
@Transactional
public class AlarmService {

    @Autowired
    private SimpMessagingTemplate template;

    public boolean sentAlarmTemperatura1(Message msg){

//        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        //Message message = Message.builder().date(321321).status("NORLAM").text("PORUKICA POSLATA").build();
//        System.out.println("TOOO");
//        System.out.println(message.getStatus());
//        System.out.println(message);
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Message message = Message.builder()
                .id(UUID.randomUUID().toString())
                .text("ALARM FIRE ON!!!")
                .status("ALARM")
                .device(msg.getDevice())
                .date(new Date().getTime())
                .type(msg.getType())
                .build();
//                    System.out.println(message);
        executor.execute(() -> template.convertAndSend("/topic/message", message));
//        executor.execute(() -> template.convertAndSend("/topic/viechle", message));

        return true;
    }

}
