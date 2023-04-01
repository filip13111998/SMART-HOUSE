package sbnz.ftn.uns.ac.rs.ADMIN.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.response.CertificateTableDTO;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Certificate;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.CertificateRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CertificateService {

    @Autowired
    private CertificateRepository cr;


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

    public Boolean delete(String uuid){



        return true;

    }

    public Boolean validate(String uuid){



        return true;

    }

}
