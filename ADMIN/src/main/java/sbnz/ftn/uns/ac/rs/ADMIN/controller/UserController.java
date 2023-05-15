package sbnz.ftn.uns.ac.rs.ADMIN.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.response.UserTableDTO;
import sbnz.ftn.uns.ac.rs.ADMIN.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'getAllUsers')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<UserTableDTO>> getAllUsers(){

        logger.info("ADMIN-APP UserController getAllUsers");

        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'removeUser')")
    @GetMapping(value = "/remove/{username}/{role}")
    public ResponseEntity<Boolean> removeUser(@PathVariable("username") String username , @PathVariable("role") String role){
        logger.info("ADMIN-APP UserController removeUser");

        return ResponseEntity.ok().body(userService.removeUser(username,role));
    }

    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'ownerRoleUser')")
    @GetMapping(value = "/owner-role/{username}/{flag}")
    public ResponseEntity<Boolean> ownerRoleUser(@PathVariable("username") String username ,
                                                  @PathVariable("flag") boolean flag ){

        logger.info("ADMIN-APP UserController ownerRoleUser");

        return ResponseEntity.ok().body(userService.ownerRoleUser(username,flag));

    }

    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'tenantRoleUser')")
    @GetMapping(value = "/tenant-role/{username}/{flag}")
    public ResponseEntity<Boolean> tenantRoleUser(@PathVariable("username") String username ,
                                                  @PathVariable("flag") boolean flag ){

        logger.info("ADMIN-APP UserController tenantRoleUser");

        return ResponseEntity.ok().body(userService.tenantRoleUser(username,flag));

    }

}
