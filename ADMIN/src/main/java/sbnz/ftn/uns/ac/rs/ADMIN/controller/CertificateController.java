package sbnz.ftn.uns.ac.rs.ADMIN.controller;

import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.request.CSRRequestDTO;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.response.CertificateTableDTO;
import sbnz.ftn.uns.ac.rs.ADMIN.service.CSRService;
import sbnz.ftn.uns.ac.rs.ADMIN.service.CertificateService;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/certificate")
public class CertificateController {

    @Autowired
    private CertificateService cc;

    //return all certificates
    @GetMapping(value = "/get-all")
    public ResponseEntity<List<CertificateTableDTO>> getAll(){

        List<CertificateTableDTO> answer = cc.getAll();

        return new ResponseEntity< List<CertificateTableDTO>>(answer, HttpStatus.OK);
    }

    @GetMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable String id) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        Boolean answer = cc.delete(id);

        return new ResponseEntity<Boolean>(answer, HttpStatus.OK);
    }

    @GetMapping(value = "/revoke/{id}")
    public ResponseEntity<Boolean> revoke(@PathVariable String id) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        Boolean answer = cc.revoke(id);

        return new ResponseEntity<Boolean>(answer, HttpStatus.OK);
    }

    @GetMapping(value = "/validate/{id}")
    public ResponseEntity<Boolean> validate(@PathVariable String id){
        Boolean answer = cc.validate(id);

        return new ResponseEntity<Boolean>(answer, HttpStatus.OK);
    }

}
