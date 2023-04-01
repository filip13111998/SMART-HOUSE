package sbnz.ftn.uns.ac.rs.ADMIN.controller;

import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.request.CSRRequestDTO;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.response.CSRResponseDTO;
import sbnz.ftn.uns.ac.rs.ADMIN.service.CSRService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class CSRController {

    @Autowired
    private CSRService csrs;

    @PostMapping(value = "/register-csr")
    public ResponseEntity<Boolean> createCSR(@RequestBody CSRRequestDTO cssrdto) throws IOException, NoSuchAlgorithmException, OperatorCreationException {

        Boolean answer = csrs.save(cssrdto);

        return new ResponseEntity<Boolean>(answer, HttpStatus.OK);
    }

    @PostMapping(value = "/verify-csr")
    public ResponseEntity<Boolean> createCSR(@RequestBody String code){
        Boolean answer = csrs.verify(code);

        return new ResponseEntity<Boolean>(answer, HttpStatus.OK);
    }

    @GetMapping(value = "/get-all")
    public ResponseEntity<List<CSRResponseDTO>> getAll(){
        List<CSRResponseDTO> answer = csrs.findAll();

        return new ResponseEntity<List<CSRResponseDTO>>(answer, HttpStatus.OK);
    }


}
