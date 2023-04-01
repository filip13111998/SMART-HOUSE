package sbnz.ftn.uns.ac.rs.ADMIN.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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


    public List<CertificateTableDTO> getAll(){

        List<CertificateTableDTO> ctdto = cr.findAll().stream().filter(c->!c.getDelete())
                .map(c->
                        CertificateTableDTO.builder()
                                .id(c.getId())
                                .issuer(c.getIssuer())
                                .subject(c.getSubject())
                                .validityStart(c.getValidityStart())
                                .validityPeriod(c.getValidityPeriod())
                                .delete(c.getDelete())
                                .build())
                .collect(Collectors.toList());

        return ctdto;

    }

    public Boolean delete(String uuid) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        System.out.println(uuid);
        Certificate certificate = cr.findById(uuid).get();
        certificate.setDelete(true);

        cr.save(certificate);

        ksi.deleteCertificate(certificate.getSubject());
        return true;

    }

    public Boolean revoke(String uuid) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        Certificate certificate = cr.findById(uuid).get();
//        certificate.setDelete(true);
        certificate.setRevoke(true);
        cr.save(certificate);

        ksi.deleteCertificate(certificate.getSubject());
        return true;
    }

    public Boolean validate(String uuid){
        Certificate certificate = cr.findById(uuid).orElse(null);

        if (certificate == null) {
            // Certificate not found, so it's valid
            return true;
        }

        if (certificate.getRevoke()) {
            // Certificate is revoked, so it's invalid
            return false;
        }

        // Certificate is not revoked, so it's valid
        return true;

    }


}
