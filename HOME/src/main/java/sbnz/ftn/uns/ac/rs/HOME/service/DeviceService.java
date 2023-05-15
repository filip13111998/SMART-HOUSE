package sbnz.ftn.uns.ac.rs.HOME.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sbnz.ftn.uns.ac.rs.HOME.dto.request.DeviceSaveDTO;
import sbnz.ftn.uns.ac.rs.HOME.model.Device;
import sbnz.ftn.uns.ac.rs.HOME.model.DeviceType;
import sbnz.ftn.uns.ac.rs.HOME.model.House;
import sbnz.ftn.uns.ac.rs.HOME.repository.DeviceRepository;
import sbnz.ftn.uns.ac.rs.HOME.repository.HouseRepository;
import sbnz.ftn.uns.ac.rs.HOME.repository.TypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeviceService {

    @Autowired
    private DeviceRepository dr;

    @Autowired
    private HouseRepository hr;

    @Autowired
    private TypeRepository tr;

    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);


    public List<String> getAllDevicesOwner(String username) {

        List<String> devices = dr.findAll().stream()
                .filter(
                        device -> device.getHouse().getOwnerList().stream().filter(o->o.getUsername().equals(username)).findFirst().isPresent()
                )
                .map(d-> d.getName()).collect(Collectors.toList());

        logger.info("HOME-APP HouseService getAllDevicesOwner.");

        return devices;

    }

    public List<String> getAllDevicesTenant(String username) {

        List<String> devices = dr.findAll().stream()
                .filter(
                        device -> device.getHouse().getTenantList().stream().filter(o->o.getUsername().equals(username)).findFirst().isPresent()
                )
                .map(d-> d.getName()).collect(Collectors.toList());

        logger.info("HOME-APP HouseService getAllDevicesTenant.");

        return devices;

    }

    public List<String> getAllDevicesAdmin() {

        List<String> devices = dr.findAll().stream().map(d-> d.getName()).collect(Collectors.toList());

        logger.info("HOME-APP HouseService getAllDevicesAdmin.");

        return devices;

    }

    public boolean saveDevice(DeviceSaveDTO dsdto){
        System.out.println(dsdto.getHouseName());
        Optional<House> house = hr.findAll().stream().filter(h->h.getName().equals(dsdto.getHouseName())).findFirst();

        if(!house.isPresent()){

            logger.warn("HOME-APP HouseService saveDevice.");

            return false;
        }

        Optional<DeviceType> deviceType = tr.findAll().stream().filter(t->t.getName().equals(dsdto.getDeviceTypeName())).findFirst();

        if(!deviceType.isPresent()){

            logger.warn("HOME-APP HouseService saveDevice.");

            return false;
        }

        Device device = Device.builder()
                .id(UUID.randomUUID().toString())
                .name(dsdto.getName())
                .deviceType(deviceType.get())
                .house(house.get())
                .build();

        house.get().getDeviceList().add(device);

        dr.save(device);

        hr.save(house.get());

        logger.info("HOME-APP HouseService saveDevice.");

        return true;
    }

}
