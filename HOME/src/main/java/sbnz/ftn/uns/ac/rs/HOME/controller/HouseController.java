package sbnz.ftn.uns.ac.rs.HOME.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sbnz.ftn.uns.ac.rs.HOME.dto.request.HouseSaveDTO;
import sbnz.ftn.uns.ac.rs.HOME.service.AlarmService;
import sbnz.ftn.uns.ac.rs.HOME.service.HouseService;
import sbnz.ftn.uns.ac.rs.HOME.utils.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/house")
public class HouseController {

    @Autowired
    private HouseService hs;

    @Autowired
    private TokenUtils tokenUtils;

    private static final Logger logger = LoggerFactory.getLogger(HouseController.class);


    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'saveHouse')")
    @PostMapping(value = "/save")
    public ResponseEntity<Boolean> saveHouse(HttpServletRequest request , @RequestBody HouseSaveDTO hsdto) {

        logger.info("HOME-APP HouseController saveHouse.");


        String jwt = tokenUtils.getToken(request);
        String role = tokenUtils.getAllClaimsFromToken(jwt).get("roles",String.class);
        String username = tokenUtils.getUsernameFromToken(jwt);

        boolean answer = hs.saveHouse(hsdto);

        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'getAllHouses')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<String>> getAllHouses(HttpServletRequest request) {

        logger.info("HOME-APP HouseController getAllHouses.");

        String jwt = tokenUtils.getToken(request);
        String role = tokenUtils.getAllClaimsFromToken(jwt).get("roles",String.class);
        String username = tokenUtils.getUsernameFromToken(jwt);

        List<String> answer = hs.getAllHouses(username);

        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

}
