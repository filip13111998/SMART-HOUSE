package sbnz.ftn.uns.ac.rs.ADMIN.controller;

import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.request.CSRRequestDTO;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.response.CSRResponseDTO;
import sbnz.ftn.uns.ac.rs.ADMIN.service.CSRService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/csr")
public class CSRController {

    @Autowired
    private CSRService csrs;

    //@PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'createCSR')")
    @PostMapping(value = "/register-csr")
    public ResponseEntity<Boolean> createCSR(@RequestBody CSRRequestDTO cssrdto) throws IOException, NoSuchAlgorithmException, OperatorCreationException {

        Boolean answer = csrs.save(cssrdto);

        return new ResponseEntity<Boolean>(answer, HttpStatus.OK);
    }

    //@PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'verifyCSR')")
    @PostMapping(value = "/verify-csr")
    public ResponseEntity<Boolean> verifyCSR(@RequestBody String code){
        Boolean answer = csrs.verify(code);

        return new ResponseEntity<Boolean>(answer, HttpStatus.OK);
    }

    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'getAllCSRS')")
    @GetMapping(value = "/get-all")
    public ResponseEntity<List<CSRResponseDTO>> getAllCSRS(){
        List<CSRResponseDTO> answer = csrs.findAll();

        return new ResponseEntity<List<CSRResponseDTO>>(answer, HttpStatus.OK);
    }

    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'acceptCSR')")
    @GetMapping(value = "/accept/{id}")
    public ResponseEntity<Boolean> acceptCSR(@PathVariable("id") String id) throws Exception {
        Boolean answer = csrs.accept(id);

        return new ResponseEntity<Boolean>(answer, HttpStatus.OK);
    }

    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'deleteCSR')")
    @GetMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> deleteCSR(@PathVariable("id") String id) throws Exception {
        Boolean answer = csrs.delete(id);

        return new ResponseEntity<Boolean>(answer, HttpStatus.OK);
    }


}
