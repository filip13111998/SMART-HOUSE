package sbnz.ftn.uns.ac.rs.HOME.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sbnz.ftn.uns.ac.rs.HOME.model.Message;
import sbnz.ftn.uns.ac.rs.HOME.repository.MessageRepository;

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

    @Autowired
    private MessageRepository mr;

    private static final Logger logger = LoggerFactory.getLogger(AlarmService.class);


    public boolean sentAlarmTemperatura1(Message msg){

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Message message = Message.builder()
                .id(UUID.randomUUID().toString())
                .text("MERAC_TEMP ALARM FIRE ON!!!")
                .status("ALARM")
                .device(msg.getDevice())
                .date(new Date().getTime())
                .type(msg.getType())
                .build();
        mr.save(message);

        executor.execute(() -> template.convertAndSend("/topic/message", message));


        logger.info("HOME-APP AlarmService sentAlarmTemperatura1.");


        return true;
    }

    public boolean sentAlarmPritisak1(Message msg){

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Message message = Message.builder()
                .id(UUID.randomUUID().toString())
                .text("MERAC_PRIT ALARM FIRE ON!!!")
                .status("ALARM")
                .device(msg.getDevice())
                .date(new Date().getTime())
                .type(msg.getType())
                .build();
        mr.save(message);

        executor.execute(() -> template.convertAndSend("/topic/message", message));


        logger.info("HOME-APP AlarmService sentAlarmTemperatura2.");


        return true;
    }

    public boolean sentAlarmVlaz1(Message msg){

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Message message = Message.builder()
                .id(UUID.randomUUID().toString())
                .text("MERAC_VLAZ ALARM FIRE ON!!!")
                .status("ALARM")
                .device(msg.getDevice())
                .date(new Date().getTime())
                .type(msg.getType())
                .build();
        mr.save(message);

        executor.execute(() -> template.convertAndSend("/topic/message", message));


        logger.info("HOME-APP AlarmService sentAlarmTemperatura2.");


        return true;
    }

}
