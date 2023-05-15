package sbnz.ftn.uns.ac.rs.HOME.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sbnz.ftn.uns.ac.rs.HOME.dto.request.DeviceSaveDTO;
import sbnz.ftn.uns.ac.rs.HOME.service.DeviceService;
import sbnz.ftn.uns.ac.rs.HOME.utils.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private TokenUtils tokenUtils;

    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);


    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'getAllDevices')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<String>> getAllDevices(HttpServletRequest request) {

        logger.info("HOME-APP DeviceController getAllDevices.");


        String jwt = tokenUtils.getToken(request);
        String role = tokenUtils.getAllClaimsFromToken(jwt).get("roles",String.class);
        String username = tokenUtils.getUsernameFromToken(jwt);

        List<String> answer = new ArrayList<>();

        if(role.equals("ROLE_ADMIN")) answer = deviceService.getAllDevicesAdmin();
        else if (role.equals("ROLE_OWNER")) answer = deviceService.getAllDevicesOwner(username);
        else answer = deviceService.getAllDevicesTenant(username);

        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'saveDevice')")
    @PostMapping(value = "/save")
    public ResponseEntity<Boolean> saveDevice(HttpServletRequest request , @RequestBody DeviceSaveDTO deviceSaveDTO) {

        logger.info("HOME-APP DeviceController saveDevice.");


        String jwt = tokenUtils.getToken(request);
        String role = tokenUtils.getAllClaimsFromToken(jwt).get("roles",String.class);
        String username = tokenUtils.getUsernameFromToken(jwt);

        boolean answer = deviceService.saveDevice(deviceSaveDTO);

        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

}
