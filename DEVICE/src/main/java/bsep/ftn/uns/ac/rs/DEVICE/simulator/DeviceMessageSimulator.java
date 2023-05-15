package bsep.ftn.uns.ac.rs.DEVICE.simulator;

import bsep.ftn.uns.ac.rs.DEVICE.model.DeviceMessage;
import bsep.ftn.uns.ac.rs.DEVICE.model.Message;
import bsep.ftn.uns.ac.rs.DEVICE.repository.DeviceRepository;
import bsep.ftn.uns.ac.rs.DEVICE.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Component
@EnableScheduling
@Transactional
public class DeviceMessageSimulator {

    @Autowired
    private DeviceRepository dr;

    @Autowired
    private MessageRepository mr;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoOperations mongoOperations;

    private static final Logger logger = LoggerFactory.getLogger(DeviceMessageSimulator.class);


    @PostConstruct
    public void recreateStoreFile(){
        mongoTemplate.dropCollection("MESSAGE");

        logger.info("DEVICE-APP DeviceMessageSimulator recreateStoreFile.");

//        String collectionName = "MESSAGE_" + new Date().getTime();
//        mongoOperations.createCollection(collectionName);
//        String path = Paths.get("").normalize().toAbsolutePath().toString();
//        String new_path = path.split("DEVICE")[0] + "/STORE/store.txt";
//
//        // Delete the file if it exists
//        try {
//            Files.deleteIfExists(Paths.get(new_path));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Create a new file
//        try {
//            Files.createFile(Paths.get(new_path));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    @Scheduled(fixedRate = 5000)
    public void generateMessage(){
//        String path = Paths.get("").normalize().toAbsolutePath().toString();
//        String new_path = path.split("DEVICE")[0] + "/STORE/store.txt";

//        AtomicReference<String> content = new AtomicReference<>("");


        dr.findAll().stream().forEach(device -> {
            Random random = new Random();
            int randomIndex = random.nextInt(device.getDeviceType().getMessages().size());
            DeviceMessage deviceMessage = device.getDeviceType().getMessages().get(randomIndex);

            Message message = Message.builder()
                    .id(UUID.randomUUID().toString())
                    .date(new Date().getTime())
                    .status(deviceMessage.getStatus())
                    .text(deviceMessage.getText())
                    .type(device.getDeviceType().getName())
                    .device(device.getName())
                    .build();

            mr.save(message);
//            content.set(content.get() + message.writeToFile());
        });

//        try (FileWriter writer = new FileWriter(new_path, true)) {
//
//                dr.findAll().stream().forEach(device -> {
//                    Random random = new Random();
//                    int randomIndex = random.nextInt(device.getDeviceType().getMessages().size());
//                    DeviceMessage deviceMessage = device.getDeviceType().getMessages().get(randomIndex);
//
//                    Message message = Message.builder()
//                            .date(new Date().getTime())
//                            .status(deviceMessage.getStatus())
//                            .text(deviceMessage.getText())
//                            .type(device.getDeviceType().getName())
//                            .build();
//                    content.set(content.get() + message.writeToFile());
//                });
////                Message message = Message.builder().build();
//            try {
//                writer.write(content.get());
//            }catch (IOException e) {
//                e.printStackTrace();
//
//            }
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
        System.out.println("GENERATE MESSAGE");

    }

}
