package sbnz.ftn.uns.ac.rs.ADMIN.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sbnz.ftn.uns.ac.rs.ADMIN.controller.AuthenticationController;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.response.ConfigDTO;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Device;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.DeviceRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConfigService {

    @Autowired
    private DeviceRepository deviceRepository;

    private static final Logger logger = LoggerFactory.getLogger(ConfigService.class);


    public List<ConfigDTO> getConfigs() {

        logger.info("ADMIN-APP ConfigService getConfigs");

        List<ConfigDTO> configDTOS = new ArrayList<>();

        String path = Paths.get("").normalize().toAbsolutePath().toString();
        String new_path = path.split("ADMIN")[0] + "/STORE/config.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(new_path))) {

            String line;

            while ((line = br.readLine()) != null) {

                String name = line.split("\\_")[0];
                String minutes = line.split("\\_")[1];
                String regex = line.split("\\_")[2];
                configDTOS.add(
                        ConfigDTO.builder()
                                .name(name)
                                .minutes(minutes)
                                .regex(regex)
                                .build()

                );
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        List<String> names = configDTOS.stream().map(c->c.getName()).collect(Collectors.toList());
        for(Device device : deviceRepository.findAll()){
            if(!names.contains(device.getName())){
                configDTOS.add(ConfigDTO.builder().name(device.getName()).minutes(null).regex(null).build());
            }
        }

        return configDTOS;
    }

    public Boolean updateConfig(ConfigDTO configDTO) {

        logger.info("ADMIN-APP ConfigService updateConfig");

        String path = Paths.get("").normalize().toAbsolutePath().toString();
        String new_path = path.split("ADMIN")[0] + "/STORE/config.txt";
        String temp_path = path.split("ADMIN")[0] + "/STORE/temp.txt"; // Temporary file path

        try (BufferedReader br = new BufferedReader(new FileReader(new_path));
             BufferedWriter bw = new BufferedWriter(new FileWriter(temp_path))) {

            String line;
            boolean flag= false;
            while ((line = br.readLine()) != null) {
                String content  = configDTO.getName() + "_" + configDTO.getMinutes().strip() + "_" + configDTO.getRegex().strip();

                String name = line.split("\\_")[0];

                if(configDTO.getName().equals(name)){
                    bw.write(content);
                    flag = true;
                    //UPDATE ONLY THAT LINE IN THT FILE with content
                }else {
                    bw.write(line);
                }
                bw.newLine(); // Add newline after each line
            }
            bw.close();
            br.close();
            // Replace the original file with the temporary file
            try {
                Files.move(Paths.get(temp_path), Paths.get(new_path), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
//            File originalFile = new File(new_path);
//            File tempFile = new File(temp_path);
//            tempFile.renameTo(originalFile);
            if(flag){
                return true;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        try (FileWriter writer = new FileWriter(new_path, true)) {
            System.out.println("USOO");
            String content  = configDTO.getName() + "_" + configDTO.getMinutes() + "_" + configDTO.getRegex();

            writer.write(content);
            writer.write(System.lineSeparator()); // Optional: Add a newline after the content

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return true;
    }


}
