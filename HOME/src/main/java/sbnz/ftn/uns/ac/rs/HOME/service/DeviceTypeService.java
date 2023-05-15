package sbnz.ftn.uns.ac.rs.HOME.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sbnz.ftn.uns.ac.rs.HOME.dto.request.DeviceTypeSaveDTO;
import sbnz.ftn.uns.ac.rs.HOME.model.DeviceMessage;
import sbnz.ftn.uns.ac.rs.HOME.model.DeviceType;
import sbnz.ftn.uns.ac.rs.HOME.repository.DeviceMessageRepository;
import sbnz.ftn.uns.ac.rs.HOME.repository.TypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
public class DeviceTypeService {

    @Autowired
    private TypeRepository tr;

    @Autowired
    private DeviceMessageRepository dmr;

    private static final Logger logger = LoggerFactory.getLogger(DeviceTypeService.class);


    public boolean saveDeviceType(DeviceTypeSaveDTO dtsdto) {

        Optional<DeviceType> devType = tr.findAll().stream().filter(t-> t.getName().equals(dtsdto.getName())).findFirst();

        if(devType.isPresent()){
            logger.warn("HOME-APP DeviceTypeService saveDeviceType.");

            return false;
        }

        List<DeviceMessage> messageList = dtsdto.getMessages().stream()
                .map(m-> DeviceMessage.builder()
                        .id(UUID.randomUUID().toString())
                        .text(m.getText())
                        .status(m.getStatus())
                        .build())
                .collect(Collectors.toList());

        dmr.saveAll(messageList);

        DeviceType deviceType = DeviceType.builder()
                .id(UUID.randomUUID().toString())
                .name(dtsdto.getName())
                .messages(messageList)
                .build();

        tr.save(deviceType);

        logger.info("HOME-APP DeviceTypeService saveDeviceType.");

        return true;
    }

    public List<String> getAllDeviceTypes() {

        List<String> deviceTypesList = tr.findAll().stream().map(t->t.getName()).collect(Collectors.toList());

        logger.info("HOME-APP DeviceTypeService getAllDeviceTypes.");

        return deviceTypesList;
    }
}
