package sbnz.ftn.uns.ac.rs.ADMIN.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.request.CSRRequestDTO;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.response.ConfigDTO;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.response.UserTableDTO;
import sbnz.ftn.uns.ac.rs.ADMIN.service.ConfigService;

import java.util.List;

@RestController
@RequestMapping(value = "/config", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConfigController {

    @Autowired
    private ConfigService configService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'getConfigs')")
    @GetMapping(value = "/get-all")
    public ResponseEntity<List<ConfigDTO>> getConfigs(){

        logger.info("ADMIN-APP ConfigController getConfigs");

        return ResponseEntity.ok().body(configService.getConfigs());
    }

    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'updateConfig')")
    @PostMapping(value = "/update")
    public ResponseEntity<Boolean> updateConfig(@RequestBody ConfigDTO configDTO){

        logger.info("ADMIN-APP ConfigController getConfigs");

        return ResponseEntity.ok().body(configService.updateConfig(configDTO));
    }

}
