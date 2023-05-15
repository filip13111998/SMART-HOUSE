package sbnz.ftn.uns.ac.rs.ADMIN.controller;

import org.bouncycastle.operator.OperatorCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private static final Logger logger = LoggerFactory.getLogger(CertificateController.class);


    //return all certificates
    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'getAllCertificates')")
    @GetMapping(value = "/get-all")
    public ResponseEntity<List<CertificateTableDTO>> getAllCertificates(){

        logger.info("ADMIN-APP CertificateController getAllCertificates.");

        List<CertificateTableDTO> answer = cc.getAll();

        return new ResponseEntity< List<CertificateTableDTO>>(answer, HttpStatus.OK);
    }

    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'deleteCertificate')")
    @GetMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> deleteCertificate(@PathVariable String id) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        logger.info("ADMIN-APP CertificateController deleteCertificate.");

        Boolean answer = cc.delete(id);

        return new ResponseEntity<Boolean>(answer, HttpStatus.OK);
    }

    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'revokeCertificate')")
    @GetMapping(value = "/revoke/{id}")
    public ResponseEntity<Boolean> revokeCertificate(@PathVariable String id) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        logger.info("ADMIN-APP CertificateController revokeCertificate.");

        Boolean answer = cc.revoke(id);

        return new ResponseEntity<Boolean>(answer, HttpStatus.OK);
    }

    @PreAuthorize("@methodLevelPermission.hasPermission(authentication,'foo', 'validateCertificate')")
    @GetMapping(value = "/validate/{id}")
    public ResponseEntity<Boolean> validateCertificate(@PathVariable String id){
        logger.info("ADMIN-APP CertificateController validateCertificate.");

        Boolean answer = cc.validate(id);

        return new ResponseEntity<Boolean>(answer, HttpStatus.OK);
    }

}
