package sbnz.ftn.uns.ac.rs.HOME.simulator;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sbnz.ftn.uns.ac.rs.HOME.model.Message;
import sbnz.ftn.uns.ac.rs.HOME.repository.MessageRepository;
import sbnz.ftn.uns.ac.rs.HOME.service.AlarmService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class Simulator {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private MessageRepository mr;

    protected final String ksessionName = "cloud-session";

    @Autowired
    private KieContainer kieContainer;

    @Autowired
    private AlarmService alarmService;

    String path = Paths.get("").normalize().toAbsolutePath().toString();
    String new_path = path.split("HOME")[0] + "/STORE/config.txt";

    @Scheduled(fixedRate = 1000)
    public void sentMessage() {
//        System.out.println("SALJEMO PORUKU" + new Date().getTime()/1000);

//        String path = Paths.get("").normalize().toAbsolutePath().toString();
//        String new_path = path.split("HOME")[0] + "/STORE/config.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(new_path))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split("\\_");

                long mills = new Date().getTime();

                System.out.println( mills/1000 % Integer.parseInt(parts[1]) );

                if(((mills/1000) % Integer.parseInt(parts[1])) == 0){

                    List<Message> messages = mr.findAll().stream()
                            .filter(m->
                                    m.getDevice().equals(parts[0])
                                            && ((mills - m.getDate()) < (Integer.parseInt(parts[1]) * 1_000))
                                            && (m.getText().matches(parts[2]))

                            )
                            .collect(Collectors.toList());
                    messages.forEach(System.out::println);
                    KieSession kieSession = kieContainer.newKieSession(ksessionName);
//                    System.out.println(alarmService == null);
                    kieSession.insert(alarmService);
//                    kieSession.setGlobal("alarmService", alarmService);
//                    kieSession.insert(order);
//                    kieSession.insert(ord2);
//                    kieSession.insert(ord3);
                    messages.forEach(m->{
                        kieSession.insert(m);
                        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                        Message message = Message.builder()
                                .id(m.getId())
                                .text(m.getText())
                                .status(m.getStatus())
                                .device(m.getDevice())
                                .date(m.getDate())
                                .type(m.getType())
                                .build();
//                    System.out.println(message);
                        executor.execute(() -> template.convertAndSend("/topic/message", message));
                    });



                    kieSession.fireAllRules();
                    kieSession.dispose();
//                    System.out.println("SIZE " + messages.size());
                    System.out.println("PATTERN: " + parts[2]);
                }

            }
            br.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }


//        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//        Message message = Message.builder().date(321321).status("NORLAM").text("PORUKICA POSLATA").build();
//        System.out.println(message);
//        executor.execute(() -> template.convertAndSend("/topic/viechle", message));
    }

}
