package sbnz.ftn.uns.ac.rs.HOME.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sbnz.ftn.uns.ac.rs.HOME.dto.request.DeviceTypeSaveDTO;
import sbnz.ftn.uns.ac.rs.HOME.dto.request.HouseSaveDTO;
import sbnz.ftn.uns.ac.rs.HOME.service.DeviceTypeService;
import sbnz.ftn.uns.ac.rs.HOME.service.HouseService;
import sbnz.ftn.uns.ac.rs.HOME.utils.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/type")
public class DeviceTypeController {

    @Autowired
    private DeviceTypeService dts;

    @Autowired
    private TokenUtils tokenUtils;

    private static final Logger logger = LoggerFactory.getLogger(DeviceTypeController.class);


    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'saveDeviceType')")
    @PostMapping(value = "/save")
    public ResponseEntity<Boolean> saveDeviceType(HttpServletRequest request , @RequestBody DeviceTypeSaveDTO dtsdto) {

        logger.info("HOME-APP DeviceTypeController saveDeviceType.");


        String jwt = tokenUtils.getToken(request);
        String role = tokenUtils.getAllClaimsFromToken(jwt).get("roles",String.class);
        String username = tokenUtils.getUsernameFromToken(jwt);

        boolean answer = dts.saveDeviceType(dtsdto);

        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'getAllDeviceTypes')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<String>> getAllDeviceTypes(HttpServletRequest request) {

        logger.info("HOME-APP DeviceTypeController getAllDeviceTypes.");


        String jwt = tokenUtils.getToken(request);
        String role = tokenUtils.getAllClaimsFromToken(jwt).get("roles",String.class);
        String username = tokenUtils.getUsernameFromToken(jwt);

        List<String> answer = dts.getAllDeviceTypes();

        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

}
