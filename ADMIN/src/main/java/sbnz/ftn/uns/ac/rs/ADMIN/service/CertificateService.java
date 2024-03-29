package sbnz.ftn.uns.ac.rs.ADMIN.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sbnz.ftn.uns.ac.rs.ADMIN.controller.CSRController;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.response.CertificateTableDTO;
import sbnz.ftn.uns.ac.rs.ADMIN.initializator.KeyStoreInitializer;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Certificate;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.CertificateRepository;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CertificateService {

    @Autowired
    private CertificateRepository cr;

    @Autowired
    private KeyStoreInitializer ksi;

    private static final Logger logger = LoggerFactory.getLogger(CertificateService.class);


    public List<CertificateTableDTO> getAll(){

        List<CertificateTableDTO> ctdto = cr.findAll().stream().filter(c->!c.getDelete())
                .map(c->
                        CertificateTableDTO.builder()
                                .id(c.getId())
                                .issuer(c.getIssuer())
                                .subject(c.getSubject())
                                .validityStart(c.getValidityStart())
                                .validityPeriod(c.getValidityPeriod())
                                .template(c.getTemplate())
                                .delete(c.getDelete())
                                .build())
                .collect(Collectors.toList());

        logger.info("ADMIN-APP CertificateService getAll.");


        return ctdto;

    }

    public Boolean delete(String uuid) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {

        Certificate certificate = cr.findById(uuid).get();

        certificate.setDelete(true);

        cr.save(certificate);

        ksi.deleteCertificate(certificate.getSubject());

        logger.info("ADMIN-APP CertificateService delete.");

        return true;

    }

    public Boolean revoke(String uuid) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {

        Certificate certificate = cr.findById(uuid).get();

//        certificate.setDelete(true);

        certificate.setRevoke(true);

        cr.save(certificate);

        ksi.deleteCertificate(certificate.getSubject());

        logger.info("ADMIN-APP CertificateService revoke.");

        return true;
    }

    public Boolean validate(String uuid){

        Certificate certificate = cr.findById(uuid).orElse(null);

        if (certificate == null) {
            // Certificate not found, so it's valid
            logger.info("ADMIN-APP CertificateService validate.");

            return true;
        }

        if (certificate.getRevoke()) {
            // Certificate is revoked, so it's invalid
            logger.warn("ADMIN-APP CertificateService validate.");

            return false;
        }

        // Certificate is not revoked, so it's valid
        logger.info("ADMIN-APP CertificateService validate.");

        return true;

    }


}
