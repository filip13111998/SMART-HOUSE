package sbnz.ftn.uns.ac.rs.HOME.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sbnz.ftn.uns.ac.rs.HOME.repository.DeviceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeviceService {

    @Autowired
    private DeviceRepository dr;


    public List<String> getAllDevicesOwner(String username) {

        List<String> devices = dr.findAll().stream()
                .filter(
                        device -> device.getHouse().getOwnerList().stream().filter(o->o.getUsername().equals(username)).findFirst().isPresent()
                )
                .map(d-> d.getName()).collect(Collectors.toList());

        return devices;

    }

    public List<String> getAllDevicesTenant(String username) {

        List<String> devices = dr.findAll().stream()
                .filter(
                        device -> device.getHouse().getTenantList().stream().filter(o->o.getUsername().equals(username)).findFirst().isPresent()
                )
                .map(d-> d.getName()).collect(Collectors.toList());

        return devices;

    }

    public List<String> getAllDevicesAdmin() {

        List<String> devices = dr.findAll().stream().map(d-> d.getName()).collect(Collectors.toList());

        return devices;

    }
}
